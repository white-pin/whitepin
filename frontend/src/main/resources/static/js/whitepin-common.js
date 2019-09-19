/**
 * Api client
 */
var apiClient = (function () {
  const endPoints = "http://localhost:3030";
  const path = {
    identification: "/mock/identification",
    join          : "/join",
    evaluation    : {
      avg   : '/evaluation/avg', // /evaluation/avg/{userToken}
      list  : '/evaluation/list', // /evaluation/list/{userToken}/{filterDivision}/{pageNumber}/{orderType}
      myList: '/evaluation/myList' // /evaluation/myList/{filterDivision}/{pageNumber}/{orderType}/{isEvaluationReceived}
    },
    user          : {
      count: "/user/count",
      info : "/user/info"
    }
  };

  var request = function (path, method, data, successHandler, errorHandler) {
    $.ajax({
      url         : endPoints + path,
      headers     : {
        dataType: 'json'
      },
      contentType : "application/json",
      type        : method,
      data        : data,
      cache       : false,
      processData : false,
      success     : function (response) {
        successHandler(response);
      }
      , error     : function (jqxhr) {
        errorHandler(jqxhr);
      }
      , beforeSend: function (jqxhr) {
        if (accountManager.isSignedIn()) {
          jqxhr.setRequestHeader("Authorization", accountManager.getLoginInfo().authToken);
        }
      },
    });
  };

  return {
    endPoints: endPoints,
    path     : path,
    request  : request
  };
})();

/**
 * Handlebars
 */
var handlebarsManager = (function () {
  var printTemplate = function (data, target, templateObject, type, prefixHtml, suffixHtml, clear) {
    if (clear) {
      console.log('clear target..');
      target.empty();
    }

    var template = Handlebars.compile(templateObject.html());
    var rendered = "";
    if (prefixHtml) {
      rendered += prefixHtml;
    }

    rendered += template(data);

    if (suffixHtml) {
      rendered += suffixHtml;
    }

    if (type === "html") {
      target.html(rendered);
    } else if (type === "append") {
      target.append(rendered);
    } else {
      console.error("invalid print template type : ", type);
    }
  };

  return {
    printTemplate: printTemplate
  };
})();

Handlebars.registerHelper("getLength", function (arr) {
  return (!arr) ? 0 : arr.length;
});

Handlebars.registerHelper("trimHashValue", function (hashValue) {
  if (validator.isEmpty(hashValue)) {
    return hashValue;
  }

  if (!hashValue.startsWith('0x')) {
    hashValue = '0x' + hashValue;
  }

  if (hashValue.length > 20) {
    return hashValue.substring(0, 20) + '...';
  }

  return hashValue;
});

Handlebars.registerHelper('ifEquals', function (arg1, arg2, options) {
  console.log('compare arg1:', arg1, ', arg2:', arg2);
  return (arg1 == arg2) ? options.fn(this) : options.inverse(this);
});

Handlebars.registerHelper("displayTimestamp", function (timestamp) {
  if (!timestamp) {
    return "";
  }

  try {
    // Copy from https://stackoverflow.com/questions/23593052/format-javascript-date-as-yyyy-mm-dd
    var d = new Date(timestamp),
      month = '' + (d.getMonth() + 1),
      day = '' + d.getDate(),
      year = d.getFullYear();

    if (month.length < 2) {
      month = '0' + month;
    }
    if (day.length < 2) {
      day = '0' + day;
    }
    return [year, month, day].join('-');
  } catch (e) {
    console.error(e);
    return 'Unknown';
  }
});

Handlebars.registerHelper('for', function (from, to, incr, block) {
  var accum = '';
  for (var i = from; i < to; i += incr) {
    accum += block.fn(i);
  }
  return accum;
});

/**
 * Account manager
 */
var accountManager = (function () {
  const loginInfoKey = 'loginInfo';
  /**
   * login 정보 조회
   */
  var getLoginInfo = function () {
    return JSON.parse(sessionStorage.getItem(loginInfoKey));
  };

  /**
   * 로그인 체크
   */
  var isSignedIn = function () {
    var loginInfo = sessionStorage.getItem(loginInfoKey);
    return typeof loginInfo != "undefined" && loginInfo != null;
  };

  var requestSignIn = function (email, password) {
    var authToken = 'Basic ' + btoa(email + ':' + password);
    console.log('authToken :: ', authToken);

    $.ajax({
      url        : apiClient.endPoints + apiClient.path.user.info,
      headers    : {
        "Authorization": 'Basic ' + btoa(email + ':' + password)
      },
      contentType: "application/json",
      type       : 'GET',
      cache      : false,
      processData: false,
      success    : function (response) {
        response.authToken = authToken;
        console.log(response);
        sessionStorage.setItem(loginInfoKey, JSON.stringify(response));

        self.location = '/account/' + response.userToken;
      }, error   : function (jqxhr) {
        alert('올바르지 않은 ID/PASSWORD 입니다.');
        console.log(jqxhr);
      }
    });
  };

  /**
   * 로그아웃
   */
  var requestSignOut = function () {
    sessionStorage.removeItem(loginInfoKey);
    self.location = '/';
  };

  /**
   * 상단 로그인 템플릿 갱신
   */
  var updateLoginTemplate = function () {
    var $target = $('#loginTemplateDiv');
    var $templateObj = $('#loginTemplate');

    var loginInfo = sessionStorage.getItem(loginInfoKey);
    console.log('validator.isEmpty(loginInfo):', validator.isEmpty(loginInfo));
    console.log('loginInfo != \'null\':', loginInfo != 'null');
    console.log('loginInfo != null:', loginInfo != null);
    var isSignedIn = !validator.isEmpty(loginInfo) && loginInfo != 'null' && loginInfo != null;

    var data = {
      "isSignedIn": isSignedIn
    };

    console.log('check login info:', loginInfo, '\n', data);
    handlebarsManager.printTemplate(data, $target, $templateObj, 'html',
      null, null, true);
  };

  return {
    getLoginInfo       : getLoginInfo,
    isSignedIn         : isSignedIn,
    requestSignIn      : requestSignIn,
    requestSignOut     : requestSignOut,
    updateLoginTemplate: updateLoginTemplate
  };
})();

/**
 * Common Validator
 */
var validator = (function () {
  var emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  var cellphoneRegex = /^[0-9]{3}[-]+[0-9]{3,4}[-]+[0-9]{4}$/;

  var isEmpty = function (input) {
    if (typeof input === "undefined" || input === '') {
      return true;
    }

    return false;
  };

  var isValidEmail = function (input) {
    return emailRegex.test(String(input).toLowerCase());
  };

  var isValidPassword = function (input) {
    if (isEmpty(input)) {
      return false;
    }

    return input.length >= 1;
  };

  var isValidCellPhone = function (input) {
    if (isEmpty(input)) {
      return false;
    }

    return cellphoneRegex.test(String(input));
  };

  return {
    isEmpty         : isEmpty,
    isValidEmail    : isValidEmail,
    isValidPassword : isValidPassword,
    isValidCellPhone: isValidCellPhone
  };
})();
