pipeline {
    agent any
    
    stages {
        
        stage('Checkout') {
            steps {
                git branch: 'main', credentialsId: '86037474-1197-462d-9747-1ebc5a995524', url: 'https://github.com/claudiaflorea/cooking-app-rest-api-mysql'
            }
       }          
        stage('Build and run tests') {

            environment {
                DATABASE_URL= 'jdbc:mysql://localhost:3306/cooking'
            }
            steps {
                sh '''./gradlew build clean -i'''
                echo "The build stage passed..."
            }
       }

//     stage('Test') { 
//           
//            environment {
//                DATABASE_URL= 'jdbc:mysql://localhost:3306/cooking?createDatabaseIfNotExist=TRUE&serverTimezone=Europe/Bucharest&autoReconnect=true&failOverReadOnly=false&maxReconnects=10'
//             }
//             steps {
//                 sh '''./gradlew build clean-DforkCount=0'''
//                 sh '''
//                     docker exec cooking sh -c 'exec mysql < ./db/dump.sql
//                     '''
//             }
//             post {
//                 always {
//                     junit 'target/surefire-reports/*.xml' 
//                 }
//             }
//         }
    }
}