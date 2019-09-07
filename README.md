# WHITE PIN API Server and Frontend  

> ## 시작하기  

**1.Server, Frontend 각각 실행하기**  

- 1-1) Server 실행  

```aidl
// windows
whitepin-api-server> .\mvnw.cmd --projects server spring-boot:run
whitepin-api-server& ./mvnw --projects server spring-boot:run
```  

- 1-2) Frontend 실행  

```aidl
$ cd frontend
$ npm install
$ npm run dev
```

**2.Server+Frontend 같이 실행하기(WORKING)**  