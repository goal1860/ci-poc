pipeline {
  agent any
  stages {
    stage('Prebuild') {
      steps {
        sh 'echo "Starting...."'
      }
    }
    stage('Deploy to RC') {
      steps {
        sh '''echo "Deploying to RC."
        echo "Doing health check..."
        echo "Deployed successfully."
        TEST_OK=\'pass\'
        echo ${TEST_OK}
        echo ${TEST_OK} > buildResults.out
        set > allVars.txt
        '''
        script {
          TEST_OK = readFile("buildResults.out").trim()
        }
        
        sh '''echo "results are:"
        echo ${TEST_OK}
        '''
        echo "${TEST_OK}"
        input(message: 'What is result of deployment?', id: 'rc_dep', ok: 'Pass')
      }
    }
    stage('Functional Test - CAPI') {
      when {
        expression {
          TEST_OK == 'pass'
        }
        
      }
      steps {
        sh '/usr/bin/mvn clean test'
      }
    }
    stage('Dependency Tests') {
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
    stage('Performance Tests') {
      steps {
        sh 'echo "Running performance tests"'
      }
    }
  }
}