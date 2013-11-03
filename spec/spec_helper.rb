require 'rspec'
require 'selenium-webdriver'
require 'selenium/webdriver/remote/http/persistent'

@client = Selenium::WebDriver::Remote::Http::Persistent.new
@client.timeout = 30

@driver = Selenium::WebDriver.for(
  :remote, 
  desired_capabilities: {
    device: 'Selendroid',
    browser_name: '',
    version: '2.3',
    app: 'PaeDaeSampleApp/bin/PaeDaeSampleApp.apk',
    'app-package' => 'com.paedae.android.sampleapp',
    'app-activity' => '.DefaultActivity'
  },
  http_client: @client,
  url: 'http://localhost:5555/wd/hub'
)

RSpec.configure do |config|
  config.after(:suite) do
    @driver.quit
  end
end
