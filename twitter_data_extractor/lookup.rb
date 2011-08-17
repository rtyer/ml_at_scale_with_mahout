#!/usr/bin/env ruby
require 'csv'
require 'bundler'
Bundler.require

if ARGV.length!=1
  abort("Invalid args:  ./lookup.rb <id>")
end

cfg = Psych.load(File.open(File.dirname(__FILE__) +'/data.yaml')) 

Twitter.configure do |config|
  config.consumer_key = cfg['twitter']['consumer_key']
  config.consumer_secret = cfg['twitter']['consumer_secret']
  config.oauth_token = cfg['twitter']['oauth_token']
  config.oauth_token_secret = cfg['twitter']['oauth_token_secret']
end

user = Twitter.user(ARGV[0].to_i);
puts "Id: #{user.id}"
puts "Screen Name: #{user.screen_name}"
puts "Followers: #{user.followers_count}"
puts "Following: #{user.friends_count}"