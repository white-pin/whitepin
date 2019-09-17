var apiClient = (function () {
  const endPoints = "http://localhost:3030";
  const path = {
    identification: "/mock/identification",
    join          : "/join"
  };

  var request = function (path, method, data, successHandler, errorHandler) {
    $.ajax({
      url        : endPoints + path,
      headers    : {
        dataType: 'json'
      },
      contentType: "application/json",
      type       : method,
      data       : data,
      cache      : false,
      processData: false,
      success    : function (response) {
        successHandler(response);
      }, error   : function (jqxhr) {
        errorHandler(jqxhr);
      }
    });
  };

  return {
    path   : path,
    request: request
  };
})();

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

var accountManager = (function () {
  /**
   * login 정보 조회
   */
  var getLoginInfo = function () {
    console.log("get login info..");
  };

  /**
   * 로그인 체크
   */
  var isSignedIn = function () {
    var loginInfo = sessionStorage.getItem("loginInfo");
    return typeof loginInfo != "undefined" && loginInfo != null;
  };

  var requestSignIn = function (email, password) {
    console.log('will request login.. with ', email, ' and ', password);
  };

  /**
   * 상단 로그인 템플릿 갱신
   */
  var updateLoginTemplate = function () {
    var $target = $('#loginTemplateDiv');
    var $templateObj = $('#loginTemplate');

    var loginInfo = sessionStorage.getItem("loginInfo");
    var isSignedIn = false;

    if (typeof loginInfo != "undefined" && loginInfo != null) {
      isSignedIn = true;
    }

    var data = {
      "isSingedIn": isSignedIn
    };

    handlebarsManager.printTemplate(data, $target, $templateObj, 'html',
      null, null, true);
  };

  return {
    getLoginInfo       : getLoginInfo,
    isSingedIn         : isSignedIn,
    requestSignIn      : requestSignIn,
    updateLoginTemplate: updateLoginTemplate
  };
})();

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

    return input.length >= 6;
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
