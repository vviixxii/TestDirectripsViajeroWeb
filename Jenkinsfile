pipeline {
    agent any
    tools {
        maven 'Maven361'
        jdk 'Java8'
    }
    stages {
        stage('Checkout Git') { 
            steps {
                git poll: true, url: 'https://github.com/vviixxii/TestDirectripsViajeroWeb.git'
            }
        }
        stage('Clean Compile Stage') {
            steps {
                echo 'Clean Compile Stage'
                sh 'mvn -Dmaven.test.failure.ignore=true clean compile'
            }
        }
        stage('Package Stage') {
            environment {
                CHROME_LIB = "${env.WORKSPACE}/libChrome"
            }
            steps {
                echo 'Package Stage'
                echo "Chromedriver Path --> ${env.CHROME_LIB}"
                //echo "--> Running ${env.WORKSPACE} <--"
                //sh 'chmod +x ${env.CHROME_DRIVER}'     
                //sh 'cd ${env.CHROME_LIB}'
                echo 'Cambio de directorio a /libChrome'
                sh 'cd /libChrome'
                echo 'Cambio de permisos de ejecución'
                sh 'chmod +x chromedriver'
                sh 'mvn -Dmaven.test.failure.ignore=true package'
            }
        }
    }
}
