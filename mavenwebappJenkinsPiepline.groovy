pipeline {
    agent any
    
    environment {
        maven_server = 'ubuntu@172.31.45.67'
        jenkins_server = 'ubuntu@172.31.41.72'
        docker_server = 'ubuntu@172.31.39.227'
    }
    
    stages {
        stage('Git Checkout') {
            steps {
                git branch: 'main', changelog: false, credentialsId: 'Git_creds', poll: false, url: 'https://github.com/AnkitSharmaDEVOPS/WebappMavenProjectMT.git'
            }
        }//Stage1closing
        stage('Send to MVN server') {
            steps {
               sshagent(['mvncreds']) {
                  sh " scp -o StrictHostKeyChecking=no -r * ${maven_server}:/home/ubuntu "
             }
            }
        }//Stage2Closing
        stage('Building the package') {
            steps {
                sshagent(['mvncreds']) {
                    sh """
                    ssh -o StrictHostKeyChecking=no ${maven_server} << EOF
                    mvn clean package
EOF
                    """
                }
            }
        }//stage3closing
        stage('Getting the Target Directory') {
            steps {
                sshagent(['mvncreds']) {
                    sh """
                    ssh -o StrictHostKeyChecking=no ${maven_server} << EOF
                    cp -r /home/ubuntu/target /tmp
                    scp -o StrictHostKeyChecking=no -r /tmp/target ${jenkins_server}:/home/ubuntu
EOF
                    """
                }
            }
        }//stage3closing
        stage('Target Transfer') {
            steps {
                sshagent(['Docker_creds']) {
                sh "scp -o StrictHostKeyChecking=no -r /home/ubuntu/target ${docker_server}:/home/ubuntu"
                 }
            }
        }//stage4closing
        stage('Docker FIle Build') {
            steps {
                sshagent(['Docker_creds']) {
                    sh """
                    ssh -o StrictHostKeyChecking=no  ${docker_server} '
                    docker build -t image_${env.BUILD_NUMBER} -f docker .
                    docker run -d --name cont_${env.BUILD_NUMBER} -p 80:8080 image_${env.BUILD_NUMBER}
                    '               
                """
                }
            }
        }//stage5closing
    }//StagesClosing
}//PipelineClosing
