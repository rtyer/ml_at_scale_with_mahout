#!/usr/bin/env ruby
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

# Twitter.followers(ARGV[0]).users.each{ |u|
#   puts "#{u.id}"  
#   Twitter.followers(u.id).users.each{ |f|
#     puts "\t#{f.id}"
#   }
# }

puts Twitter.rate_limit_status
