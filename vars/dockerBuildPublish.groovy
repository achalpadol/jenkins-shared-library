def call(Map config) {

    def app

    String image = config.image
    String credential = config.credential
    String registry = config.registry ?: 'https://index.docker.io/v1/'

    stage('Docker Build') {
        script {
            echo "Building Docker image: ${image}:${env.BUILD_NUMBER}"

            app = docker.build(
                "${image}:${env.BUILD_NUMBER}"
            )
        }
    }

    stage('Docker Publish') {
        script {
            echo "Publishing Docker image..."

            docker.withRegistry(
                registry,
                credential
            ) {
                app.push()
                app.push("latest")
            }
        }
    }
}
