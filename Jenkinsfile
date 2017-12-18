pipeline {
  agent any
  parameters {
          string(

              defaultValue: 'fail',
              description: 'Deployment successful?',
              name: 'DP_OK')
      }
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
        ${DP_OK}="pass"
      }
    }
    stage('Functional Test - CAPI') {
      when {

          expression { params.DP_OK == 'pass' }
      }
      steps {
        sh '/usr/bin/mvn clean test'
      }
    }
    stage('Approve CAPI') {
      steps {
        input 'Can we approve CAPI test result?'
      }
    }
    stage('Run Shop Tests') {
      parallel {
        stage('Run Shop Tests') {
          steps {
            sh 'echo "Running shop tests"'
          }
        }
        stage('Run Orion Tests') {
          steps {
            sh 'echo "Running Orion tests."'
          }
        }
      }
    }
    stage('Approve Dependencies') {
      steps {
        input(message: 'Can we approve dependency tests?', ok: 'Approve')
      }
    }
    stage('Performance Tests') {
      steps {
        sh 'echo "Running performance tests"'
      }
    }
    stage('Approve Performance Tests') {
      steps {
        input(message: 'Do we approve the performance test result?', ok: 'Approve')
      }
    }
  }
}
