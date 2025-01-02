
package com.yourpackage;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText reminderDate, reminderTime;
    private Button setReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reminderDate = findViewById(R.id.reminder_date);
        reminderTime = findViewById(R.id.reminder_time);
        setReminder = findViewById(R.id.set_reminder);

        setReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = reminderDate.getText().toString();
                String time = reminderTime.getText().toString();

                if (!date.isEmpty() && !time.isEmpty()) {
                    Calendar calendar = Calendar.getInstance();
                    String[] dateParts = date.split("-");
                    String[] timeParts = time.split(":");

                    calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateParts[0]));
                    calendar.set(Calendar.MONTH, Integer.parseInt(dateParts[1]) - 1);
                    calendar.set(Calendar.YEAR, Integer.parseInt(dateParts[2]));
                    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeParts[0]));
                    calendar.set(Calendar.MINUTE, Integer.parseInt(timeParts[1]));
                    calendar.set(Calendar.SECOND, 0);

                    Intent intent = new Intent(MainActivity.this, ReminderReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                    Toast.makeText(MainActivity.this, "Reminder set!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Please enter both date and time!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
