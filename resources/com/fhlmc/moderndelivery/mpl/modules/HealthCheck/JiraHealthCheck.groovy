
import com.fhlmc.moderndelivery.mpl.*


import java.net.HttpURLConnection;
import java.net.URL;

print 'Checking JIRA Status....'

HttpURLConnection urlConnection;
def responseCode

try {
    urlConnection = (HttpURLConnection) new URL(targetUrl).openConnection()
    urlConnection.setRequestMethod("HEAD")
    urlConnection.setConnectTimeout(2000)
    urlConnection.setReadTimeout(2000)
    responseCode = urlConnection.getResponseCode();

} catch (Exception e) {
    responseCode = 0
}


if(responseCode == 200)
{
    println '200 OK'
}
else
{
    println responsecode + ' FAIL'
    throw new MPLModuleException("Jira health check failed: Response code " + responseCode)
}