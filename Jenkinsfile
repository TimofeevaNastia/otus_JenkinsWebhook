pipeline {
    agent any
       environment {
            LC_ALL = 'en_US.UTF-8'
            LANG    = 'en_US.UTF-8'
            LANGUAGE = 'en_US.UTF-8'
        }
    tools {
        maven 'maven'
    }

    triggers {
        githubPush()
    }

    parameters {
        string(name: 'GIT_URL', defaultValue: 'https://github.com/TimofeevaNastia/otus_JenkinsWebhook.git', description: 'The target git url')
        string(name: 'GIT_BRANCH', defaultValue: 'JenkinsWebhook', description: 'The target git branch')
        choice(name: 'BROWSER_NAME', choices: ['chrome', 'firefox'], description: 'Pick the target browser in Selenoid')
        choice(name: 'BROWSER_VERSION', choices: ['89.0', '88.0', '78.0'], description: 'Pick the target browser version in Selenoid')
    }
    stages {
   stage('Pull from GitHub') {
            steps {
                slackSend(message: "Notification from Jenkins Pipeline:Job begin")
                git ([
                    url: "${params.GIT_URL}",
                    branch: "${params.GIT_BRANCH}"
                    ])
            }
        }
        stage('Run maven clean test') {
            steps {
                bat 'mvn clean test -Dbrowser_name=$BROWSER_NAME -Dbrowser_version=$BROWSER_VERSION'
            }

        }
        stage('Backup and Reports') {
            steps {
                archiveArtifacts artifacts: '**/target/', fingerprint: true
            }
            post {
                always {
                  script {
                     // Узнаем ветку репозитория
                      def branch = bat(returnStdout: true, script: 'git rev-parse --abbrev-ref HEAD\n').trim().tokenize().last()
                      println("branch= " + branch)
                      // Достаем информацию по тестам из junit репорта
                      def summary = junit testResults: '**/target/surefire-reports/*.xml'
                      println("summary generated")
                      // Текст оповещения
                      def message = "${currentBuild.currentResult}: Job ${env.JOB_NAME}, build ${env.BUILD_NUMBER}, branch ${branch}\nTest Summary - ${summary.totalCount}, Failures: ${summary.failCount}, Skipped: ${summary.skipCount}, Passed: ${summary.passCount}\nMore info at: ${env.BUILD_URL}"
                      if (currentBuild.currentResult == 'SUCCESS') {
                      step([$class: 'Mailer', notifyEveryUnstableBuild: true, recipients: "anasok1997@gmail.com", sendToIndividuals: true])
                      slackSend(message: "Notification from Jenkins Pipeline: "+ message)
                      } else {
                     step([$class: 'Mailer', notifyEveryUnstableBuild: true, recipients: "anasok1997@gmail.com", sendToIndividuals: true])
                      slackSend(message: "Notification from Jenkins Pipeline: "+ message)
                      }


                    // Формирование отчета
                      allure([
                      includeProperties: false,
                      jdk: '',
                      properties: [],
                      reportBuildPolicy: 'ALWAYS',
                      results: [[path: 'target/allure-results']]
                    ])
                    println('allure report created')
                    // Текст оповещения
                    //def message = "${currentBuild.currentResult}: Job ${env.JOB_NAME}, build ${env.BUILD_NUMBER}, branch ${branch}\nTest Summary - ${summary.totalCount}, Failures: ${summary.failCount}, Skipped: ${summary.skipCount}, Passed: ${summary.passCount}\nMore info at: ${env.BUILD_URL}"
                    println("message= " + message)
                  }

                }

            }
        }
    }
}