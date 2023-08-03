require 'sinatra'

# Route for the root endpoint
get '/' do
  "Hello World\nRuby Version: #{RUBY_VERSION}\n"
end
