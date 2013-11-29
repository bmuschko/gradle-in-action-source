binaryRepository {
    baseUrl = 'http://localhost:8081/artifactory'
    releaseUrl = "$baseUrl/libs-release-local"
}

environments {
    local {
        server {
            hostname = 'localhost'
            sshPort = 2222
            username = 'vagrant'
        }

        tomcat {
            hostname = '193.168.1.33'
            port = 8080
            context = 'todo'
        }
    }
}