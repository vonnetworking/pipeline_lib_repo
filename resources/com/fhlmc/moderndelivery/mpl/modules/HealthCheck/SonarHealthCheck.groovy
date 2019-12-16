
import com.fhlmc.moderndelivery.mpl.*


import java.net.HttpURLConnection;
import java.net.URL;

print 'Checking Sonarqube Status....'

HttpURLConnection urlConnection;
def responseCode
def targetUrl = 'https://pilot.agiletrailblazers.com/sonarqube'

try {
    urlConnection = (HttpURLConnection) new URL(targetUrl).openConnection()
    urlConnection.setRequestMethod("HEAD")
    urlConnection.setConnectTimeout(2000)
    urlConnection.setReadTimeout(2000)
    responseCode = urlConnection.getResponseCode();

} catch (Exception e) {
    e.printStackTrace()
    responseCode = 0
}


if(responseCode == 200)
{
    print 'OK'
    println ''
}
else
{
    println responseCode + ' FAIL'
    throw new MPLModuleException("Sonarqube health check failed: Response code " + responseCode)
}