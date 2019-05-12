pipeline {
    agent any
    environment { 
        driver = "${env.WORKSPACE}" '/libChrome/chromedriver'
    }
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
            steps {
                echo 'Package Stage'
                echo "Chromedriver Path --> ${env.driver}"
                //echo "--> Running ${env.WORKSPACE} <--"
                //sh 'chmod 400 "${env.WORKSPACE}"/libChrome/chromedriver'                
                sh 'mvn -Dmaven.test.failure.ignore=true package'
            }
        }
    }
}
