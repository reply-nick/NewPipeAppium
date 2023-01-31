# Mobile Automation for the NewPipe app
**Java + TestNG + Maven + Appium**

- Page Object Model
- Screenshot on failure
- Screen Recording
- Allure Reports

## Appium Installation
`npm install -g appium`

## How to run

`cd {repo folder}`

`mvn clean test`

## How to generate report

Install Allure

`sudo npm i -g allure-commandline`

Generate report

`cd {repo folder}`

`allure serve -h localhost allure-results`
