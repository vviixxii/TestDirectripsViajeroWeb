pipeline {
    agent any
    tools {
        maven 'Maven361'
        jdk 'Java8'
    }
    stages {
        stage('Checkout Git') { 
            steps {
                git poll: true, url: 'https://github.com/vviixxii/pruebas-selenium.git'
            }
        }
        stage('Clean Stage') {
            steps {
                echo 'Clean Stage'
                sh 'mvn -Dmaven.test.failure.ignore=true clean'
            }
        }
        stage('Compile Stage') {
            steps {
                echo 'Compile Stage'
                sh 'mvn -Dmaven.test.failure.ignore=true compile'
            }
        }
    }
}