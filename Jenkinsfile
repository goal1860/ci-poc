pipeline {
  agent any
  stages {
    stage('Prebuild') {
      steps {
        sh 'echo "Starting..."'
      }
    }
    stage('Deploy to RC') {
      steps {
        sh '''echo "Deploying to RC."
        echo "Doing health check..."
        echo "Deployed successfully."'''
      }
    }
    stage('Functional Test') {
      steps {
        sh "'${mvnHome}/bin/mvn' clean test"
      }
    }
  }
}