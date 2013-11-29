group { 'puppet':
  ensure => 'present'
}

class java_6::install {
  exec { "update-package-list":
    command => "/usr/bin/sudo /usr/bin/apt-get update",
  }
  
  package { "openjdk-6-jdk":
    ensure => installed,
    require => Exec["update-package-list"]
  }
}

include java_6::install

class h2 {
  include java_6::install
  
  exec { "create_schema":
    command => "/usr/bin/java -cp /vagrant/files/h2-1.3.172.jar org.h2.tools.RunScript -url jdbc:h2:~/todo -script /vagrant/files/todo-schema.sql",
    user => 'tomcat',
    creates => "/home/tomcat/todo.h2.db"
  }
}

include h2

Class["java_6::install"] -> Class["h2"]

class tomcat7::prereqs {
  if !defined(Group['tomcat']) {
    group { "tomcat" :
      ensure => "present"
    }
  }

  if !defined(User['tomcat']) {
    user { "tomcat" :
      ensure => "present",
      gid => "tomcat",
      managehome => true,
      require => Group['tomcat']
    }
  }
}

class tomcat7 {
  include tomcat7::prereqs
  $tomcat_version = "7.0.42"
  $tomcat_name = "apache-tomcat-${tomcat_version}"
  $tomcat_download_url = "http://archive.apache.org/dist/tomcat/tomcat-7/v${tomcat_version}/bin/${tomcat_name}.tar.gz"
  $tomcat_install_dir = "/opt/tomcat/${tomcat_name}"

  exec {
    "download_tomcat7":
      cwd => "/tmp",
      command => "/usr/bin/wget http://archive.apache.org/dist/tomcat/tomcat-7/v7.0.42/bin/apache-tomcat-7.0.42.tar.gz",
      creates => "/tmp/apache-tomcat-7.0.42.tar.gz";

    "unpack_tomcat7":
      cwd => "/opt",
      command => "/bin/tar -zxf /tmp/apache-tomcat-7.0.42.tar.gz",
      creates => "/opt/apache-tomcat-7.0.42",
      require => Exec["download_tomcat7"]
  }

  file { "/opt/apache-tomcat-7.0.42":
    recurse => true,
    owner => 'tomcat',
    group => 'tomcat',
    require => [ Exec['unpack_tomcat7'], Class['tomcat7::prereqs'] ]
  }
  
  service { 'tomcat':
    provider => "init",
    ensure => running,
    start => "sudo -u tomcat /opt/apache-tomcat-7.0.42/bin/startup.sh",
    stop => "sudo -u tomcat /opt/apache-tomcat-7.0.42/bin/shutdown.sh",
    status => "",
    restart => "",
    hasstatus => false,
    hasrestart => false,
    require => [ Exec['unpack_tomcat7'], Class['tomcat7::prereqs'] ],
  }
  
  file { "/opt/apache-tomcat-7.0.42/conf/tomcat-users.xml": 
    owner => 'tomcat',
    group => 'tomcat',
    source => "/vagrant/files/tomcat-users.xml",
    notify => Service['tomcat'],
    require => [ Exec['unpack_tomcat7'], Class['tomcat7::prereqs'] ]
  }
}

include tomcat7

Class["java_6::install"] -> Class["tomcat7"]