pipeline {
  agent any
  stages {
    stage('Init') {
      steps {
        sh 'echo "Starting..."'
      }
    }
    stage('Deploy to RC') {
      steps {
        sh 'echo "Deploying to RC."'
        script {
          env.DEP_RC=input(
            message: 'What is result of deployment?', id: 'DEP_RC_OK', ok: 'Submit',
            parameters:[choice(name: 'DEP_RC_OK', choices: 'pass\nfail', description: 'What is result of deployment?')]
          )
          echo ("${env.DEP_RC}")
        }
        
      }
    }
    stage('Functional Test - CAPI') {
      when {
        expression {
          env.DEP_RC == 'pass'
        }
        
      }
      steps {
        sh '/usr/bin/mvn clean test'
      }
      post {
        always {
          junit '**/target/surefire-reports/**/*.xml'
          
        }
        
        success {
          script {
            env.AT_RC == 'pass'
          }
          
          
        }
        
        failure {
          script {
            env.AT_RC == 'fail'
          }
          
          
        }
        
      }
    }
    stage('Dependency Tests') {
      when {
        expression {
          env.AT_RC == 'pass'
        }
        
      }
      parallel {
        stage('Run Shop Tests') {
          steps {
            sh 'echo "Running shop tests."'
          }
        }
        stage('Run Orion Tests') {
          steps {
            sh 'echo "Running Orion tests."'
          }
        }
      }
    }
  }
}
