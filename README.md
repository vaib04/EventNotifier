# EventNotifier
[Android] Scan QR code in an event's poster and save the date, time and venue as a calendar appointment.
<br><br>
All the QR codes can be read by this app. But to create calendar events, QR codes containing data in a particular format is needed.<br><br>
<b>Format of QR codes</b> to be used to enable <b>creation of calendar events</b>: <br>
    QR codes must contain plain text in the following order:
        <pre>title,location,startYear,startMonth,startDay,startHour,startMinute,endYear,endMonth,endDay,endHour,endMinute,description</pre>
     where, <br><b>title</b>: The title for calendar event. [String value]<br>
            <b>location</b>: Location of the event [String value]<br>
            <b>startYear</b>: year at which event starts (YYYY) [Integer value]<br>
            <b>startMonth</b>: month in which event starts (MM) [Integer value]<br>
            <b>startDay</b>: day at which event starts (DD) [Integer value]<br>
            <b>startHour</b>: hour at which event starts (hh) [Integer value]<br>
            <b>startMonth</b>: minute at which event starts (mm) [Integer value]<br>
            Similarly for endYear,endMonth,endDay,endHour,endMinute<br>
            <b>description</b>Value to be filled in Calendar's description field. [String value]<br>
