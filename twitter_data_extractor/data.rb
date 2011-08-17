#!/usr/bin/env ruby
require 'csv'
require 'bundler'
Bundler.require

if ARGV.length!=2
  abort("Invalid args:  ./data.rb <user> <output_file>")
end

cfg = Psych.load(File.open(File.dirname(__FILE__) +'/data.yaml')) 

Twitter.configure do |config|
  config.consumer_key = cfg['twitter']['consumer_key']
  config.consumer_secret = cfg['twitter']['consumer_secret']
  config.oauth_token = cfg['twitter']['oauth_token']
  config.oauth_token_secret = cfg['twitter']['oauth_token_secret']
end

CSV.open(ARGV[1], "w") do |csv|
  user_id = Twitter.user(ARGV[0]).id
  cursor = -1
  friends = []
  begin
    response = Twitter.friends(ARGV[0], :cursor => cursor)
    friends += response.users
    cursor = response.next_cursor
  end while cursor > 0
  
  puts "following: #{friends.length}"
  friends.each{ |u|
    csv << [user_id, u.id] 
  }
  friends.each{ |u|
    begin
      inner_cursor = -1
      user_friends = []
      begin
        inner_response = Twitter.friends(u.id, :cursor => inner_cursor)
        user_friends += inner_response.users
        inner_cursor = inner_response.next_cursor
      end while inner_cursor >0
      puts "following: #{user_friends.length}"      
      user_friends.each{ |f|
        csv << [u.id, f.id]
      }
    rescue Exception
      puts "error getting friends for user #{u.id}"
    end
  }
end

puts Twitter.rate_limit_status
