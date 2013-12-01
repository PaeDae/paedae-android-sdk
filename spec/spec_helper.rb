require 'rspec'
require 'selenium-webdriver'
require 'selenium/webdriver/remote/http/persistent'

@client = Selenium::WebDriver::Remote::Http::Persistent.new
@client.timeout = 300

@driver = Selenium::WebDriver.for(
  :remote, 
  desired_capabilities: {
    device: 'Selendroid',
    browser_name: '',
    version: '2.2',
    aut: 'com.paedae.android.sampleapp:1.0'
  },
  http_client: @client,
  url: 'http://localhost:5555/wd/hub'
)

RSpec.configure do |config|
  config.after(:suite) do
    @driver.quit
  end
end
