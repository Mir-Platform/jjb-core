def meta_location = params.META_LOCATION
def git_cred_id = params.GIT_CRED_ID
def git_branch = params.GIT_BRANCH

pipeline {
    agent {
        label 'common-centos'
    }

    stages {
        stage('Clone git repos') {
            steps {
                echo 'Clone meta'
                dir('meta') {
                    git credentialsId: "${git_cred_id}",
                    url: 'git@github.com:Mir-Platform/meta-example.git'
                }
                echo 'Clone bitbucket-core-automation'
                dir('auto') {
                    git credentialsId: "${git_cred_id}",
                    branch: git_branch,
                    url: 'git@github.com:Mir-Platform/bitbucket-core-automation.git'
                }
            }
        }

        stage('Install and run') {
            steps {
                echo 'Install requirements'

                withDockerContainer('python:3.8.2-slim') {
                    withEnv(["HOME=${env.WORKSPACE}"]) {
                    sh 'pip install --user --upgrade -r auto/requirements.txt'
                    sleep(5)
                    echo 'Run automation'
                    withCredentials([usernamePassword(credentialsId: 'bitbucket_tech', passwordVariable: 'pass', usernameVariable: 'user')]) {
                        dir('auto') {
                                sh "./run.py -b 'http://stash.example' -u $user -p $pass -f ../meta/${meta_location}"
                            }
                        }
                    }
                }
            }
        }
    }
}