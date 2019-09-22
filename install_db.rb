#!/usr/bin/ruby

require 'mysql2'

begin
    print "\n"
    print "This script installs the FISH Oracle database, example meta data and
a mysql user to have explicit access to the FISH Oracle database. It
assumes that a mysql server is already installed and that you have root
access to the mysql server.\n"
    print "\n"
    print "Enter the mysql root password: "
    system "stty -echo"
    rpw = gets
    system "stty echo"
    con = Mysql2::Client.new(:host => "localhost", :username => "root", :password => rpw.chomp)
    
    puts "\n"
    
    fopw1 = "0"
    fopw2 = "1"
    
    while !fopw1.eql?(fopw2)
      system "stty -echo"
      print "Enter new password for mysql user 'fishoracle': "
      fopw1 = gets
      system "stty echo"
      puts "\n"
      
      print "Retype password: "
      system "stty -echo"
      fopw2 = gets
      system "stty echo"
      
      puts "\n"
      
      if(!fopw1.eql?(fopw2))
        puts "Passwords don't match. Please retry.\n\n"
      end 
      
      fopw = fopw1.chomp
      
    end
    
    puts "Create mysql user fishoracle..."
    
    con.query('CREATE USER fishoracle@localhost IDENTIFIED BY \'' + fopw + '\'')
    con.query('GRANT ALL ON fishoracle.* to fishoracle@localhost')
    
    puts "Create fishoracle database..."
    con.query('CREATE DATABASE fishoracle')
    con.query('USE fishoracle')

    puts "Import data..."

    system "mysql -u root --password=" + rpw.chomp + " fishoracle < emptyoracle.sql"
    system "mysql -u root --password=" + rpw.chomp + " fishoracle < data.sql"
    
    puts "Setup admin user..."
    con.query("INSERT INTO user
    (user_id, first_name, last_name, username, email, password, isactive, isadmin)
    VALUES 
    (1, 'Admin', 'Admin', 'admin', 'admin@example.com', 'd033e22ae348aeb5660fc2140aec35850c4da997', 1, 01)"
    )

rescue Mysql2::Error => e
    puts e.errno
    puts e.error
    
ensure
    con.close if con
end
