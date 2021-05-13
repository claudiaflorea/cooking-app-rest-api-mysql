pipeline {
    agent any

    stages {
        
        stage('Checkout') {
            steps {
                git branch: 'main', credentialsId: '86037474-1197-462d-9747-1ebc5a995524', url: 'https://github.com/claudiaflorea/cooking-app-rest-api-mysql'
            }
       }
          
      stage('Build and run tests') {
            steps {
                sh '''./gradlew build clean'''
                echo "The build stage passed..."
            }
       }
    }
}