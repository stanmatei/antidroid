package matei.mple.com.useless;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {
TextView abut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        abut=findViewById(R.id.abut);
        String s="HELP & ABOUT \n Here are all the commands:\n w:(insert words)= search wikipedia, g:(insert words)=search google \n b:(insert words)=search bing, r:(insert subreddit) \n" +
                "w/(city) search for weather in a city, t/ get current time and date \n c/(phone number) call  number,h/ get today's news headline \n Type the package name of an app to open it (can be found under each app in the menu)\n" +
                "type 'menu' to view all apps and package names \n Shortcuts: \n a/ =view all saved shortcurts, d/(shortcut name)= delete that shortcut \n Type: n/(shortcutname)=(expression shortcutted) to save a new shortcut\n" +
                "s/(shortcut name) = access the shortcut \n The Anti-Launcher developed by Matei Stan ===> 13 Software\n Type 'suggestion' to send an email with suggestions for the app!";
        abut.setText(s);
    }
}
