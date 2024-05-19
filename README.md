# FitVenture
![ ](https://git.chalmers.se/courses/dit113/2024/group-7/fitventure/-/raw/main/src/main/resources/fitVenture/ui/images/fitVentureLogo2.jpg?ref_type=heads)
# Project demo
FitVenture - https://youtu.be/ntruZjtUkDI

# What is FitVenture (Purpose and Benefits)?

> FitVenture is a free-to-use application which was made to change the way people look at fitness. This is done through interactive features such as weight and run goals, daily challenges, and the main feature achievements.

> Achievements visualise the comparison between the covered distance or calories burnt with the length of either real world objects or daily calorie consumption of animals. The satisfying feeling of completing something makes it easier for FitVenture users to be more active. 

> FitVenture customers can create their own running and weight goals and keep track of their progress via the Bar Charts and Progress Bars. Additionally, users get daily challenges to motivate them to start their day by completing them.



# Features?
- Completely FREE fitness application 
- MQTT based application 
- Uses publish-subscribe pattern
- Uses Embedded system for gathering data 
- Visualisation utilizing bar charts and progress bars 
- Goals, daily challenges and achievements 
- Functionality to change personal data  

# How it works ?
![ ](https://git.chalmers.se/courses/dit113/2024/group-7/fitventure/-/raw/main/src/main/resources/fitVenture/ui/images/SystemDiagram.png?ref_type=heads)
The Hardware part of the system, Arduino Wio terminal is connected to the computer via the USB Type-c cable. Wio terminal and client side of the application share information between each other via the MQTT Broker topics, utilizing publish-subscribe pattern. Wio terminal is equipped with 3 sensors through 4 pin cables, and the desktop application has a Back-End and UI sides connected between each other through controllers.

### Software architecture
- The user registers, and can optionally fill in personal information, which the system saves into the JSON file. 
- The user logs in and goes to the main dashboard, where they can see their progress and statistics via the Bar Chart.  
- On the main dashboard screen the user can see the temperature & humidity values from the [sensor.](https://wiki.seeedstudio.com/Grove-TemperatureAndHumidity_Sensor/) 
- Goals, daily challenges and achievements pages are accessible from the main dashboard. 

### Hardware architecture

- [Arduino Wio Terminal](https://wiki.seeedstudio.com/Wio-Terminal-Getting-Started/) is equipped with [three components](https://git.chalmers.se/courses/dit113/2024/group-7/fitventure/-/wikis/Components) which are connected via the 4-pin cables 
- Temperature & humidity values are visible on the [Arduino Wio Terminal](https://wiki.seeedstudio.com/Wio-Terminal-Getting-Started/) screen 
- XYZ Coordinate Data from the [Accelerometer Sensor](https://wiki.seeedstudio.com/Grove-3-Axis_Digital_Accelerometer-1.5g/) is converted to the distance measured in meters, which is used to calculate steps and calories burned.
- [Button component](https://wiki.seeedstudio.com/Grove-Button/) is used to start/finish an exercise session.
- The Wio-terminal and PC are connected utilizing the publish-subscribe pattern via the MQTT Broker. 

# Main contribution 
_PS: Every group member wrote their personal opinion about the main contribution_

#### Danylo Baranov: 
> The PM for the period of the whole project, worked with git workflow organisation. Implemented login and registration page, JSON and project structure organisation, time chart for goals, start/end timer for the race (WIO, Back-end, MQTT), Folder structure fix (.gitignore, maven dependencies), worked with the bug fix of the publishing to the MQTT. Helped teammates with bug fixes throughout the project. 

#### Oleksandr Kozlov:
>  The initial UI and the logic behind User Profile, Goals Page and the complete redesign of the whole application UI towards the end of the project. Also, worked partially with the MQTT publish/subscribe of the user’s info and the sensor data.

#### Danis Music:
> Worked on the additional optional information in the registration page, the continuous integration pipelines for both the java application aswell as the arduino.  Worked on the achievements feature aswell. Fixed sourceDirectory issue aswell as created the content to .gitignore file aswell as creating a publish subcsribe connection from the application to the embedded system to send the user weight and height to the arduino.

#### Abdullahi Mahamed:
> Focused mostly on ensuring the sensor functionality by integrating the accelerometer, temperature and humidity sensors and successfully publishing their readings to our desktop application. Have established the MQTT connection between the Wio Terminal and the desktop app and also developed the UI on the Wio Terminal to display the temperature and humidity data and suggested activity in real-time.

#### Jackson Niyomugabo:
> Ardunino's connection notification to the user, Progress visualization of data (Creation of Bar chart for day, week and month). Setting of weight goals and saving of the goals to the json file. Goals completion notification for both weight goal and running goals 

#### Stefan Tram:
> Mainly developed the data saving structure/logic and improved the way data is shown on charts. Contributed to CI pipelines for the Java application and Arduino. Also worked on achievements. Created the base structure for time/date utils.


# Run the latest version 
` 1. Clone the repository: $ git clone https://git.chalmers.se/courses/dit113/2024/group-7/fitventure `

`2. Find the project directory: $ cd FitVenture/`

# User manual 
1. Once the user opens the application they go to the registration-login page. If they don't have an account and have not used our application they can go the the registration page **(Red Box 1)**. In case they already used our application they can go to the login page **(Red Box 2)**

![ ](https://git.chalmers.se/courses/dit113/2024/group-7/fitventure/-/raw/main/src/main/resources/fitVenture/ui/images/UserManualPages/LoginRegistrationPage.jpg?ref_type=heads)

2. Once the user opens the registration page they can register by filling in the username and password fields **(Red Box 1)**. If the user wants to add additional information they can press on the "additional information button" and fill in the fields **(Red Box 2)**

![ ](https://git.chalmers.se/courses/dit113/2024/group-7/fitventure/-/raw/main/src/main/resources/fitVenture/ui/images/UserManualPages/RegistrationPage.png?ref_type=heads)

3. If the user went to the login page they can fill in their username and password in the fields, then press the login button, to get into the application.

![](https://git.chalmers.se/courses/dit113/2024/group-7/fitventure/-/raw/main/src/main/resources/fitVenture/ui/images/UserManualPages/LoginPage.png?ref_type=heads)

4. Once the user logs in or registers to the application they go to the main dashboard, where they can see the chart showing Steps, Calories and Distance over a time interval. They can open achievements, goals, or challenges by pressing the button on the left page of the screen **(Red Box 1)**. They can also choose  from what time interval the chart should show **(Red Box 2)**. The user profile can be accessed by pressing the user profile button **(Red Box 3)**.

![](https://git.chalmers.se/courses/dit113/2024/group-7/fitventure/-/raw/main/src/main/resources/fitVenture/ui/images/UserManualPages/MainDashboardPage.png?ref_type=heads)

5. If the user goes to the user profile page they can change their password and username **(Red Box 2)**. The users weight, height and full name can be also be edited by pressing the "Edit data" button **(Red Box 1)**

![](https://git.chalmers.se/courses/dit113/2024/group-7/fitventure/-/raw/main/src/main/resources/fitVenture/ui/images/UserManualPages/UserProfilePage.png?ref_type=heads)

6. If the user opened the goals page they can see their completed goals for the past week or month period **(Red Box 3)**. They are also able to create and see either weight goals **(Red Box 1)** or running goals **(Red Box 2)**

![](https://git.chalmers.se/courses/dit113/2024/group-7/fitventure/-/raw/main/src/main/resources/fitVenture/ui/images/UserManualPages/GoalsPage.png?ref_type=heads)

7. If the user goes to the daily challenges page they can see their steps, distance and calorie challenges for the day. **(img below)**

![](https://git.chalmers.se/courses/dit113/2024/group-7/fitventure/-/raw/main/src/main/resources/fitVenture/ui/images/UserManualPages/ChallendgesPage.png?ref_type=heads)

8. If the user goes to the achievements page, they can see how close they are to completion. Light purple colour shows that achievement is not done yet and that the user has more work to do. When the achievement turns dark purple, the user has completed an achievement. 

![](https://git.chalmers.se/courses/dit113/2024/group-7/fitventure/-/raw/main/src/main/resources/fitVenture/ui/images/UserManualPages/AchievementPage.png?ref_type=heads)







