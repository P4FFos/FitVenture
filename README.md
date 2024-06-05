# FitVenture
![ ](https://github.com/P4FFos/FitVenture/blob/main/src/main/resources/fitVenture/ui/images/fitVentureLogo2.jpg?raw=true)
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
![ ](https://github.com/P4FFos/FitVenture/blob/main/src/main/resources/fitVenture/ui/images/SystemDiagram.png?raw=true)

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

# Run the latest version 
` 1. Clone the repository: $ git clone https://git.chalmers.se/courses/dit113/2024/group-7/fitventure `

`2. Find the project directory: $ cd FitVenture/`

# User manual 
1. Once the user opens the application they go to the registration-login page. If they don't have an account and have not used our application they can go the the registration page **(Red Box 1)**. In case they already used our application they can go to the login page **(Red Box 2)**

![ ](https://github.com/P4FFos/FitVenture/blob/main/src/main/resources/fitVenture/ui/images/UserManualPages/LoginRegistrationPage.jpg?raw=true)

2. Once the user opens the registration page they can register by filling in the username and password fields **(Red Box 1)**. If the user wants to add additional information they can press on the "additional information button" and fill in the fields **(Red Box 2)**

![ ](https://github.com/P4FFos/FitVenture/blob/main/src/main/resources/fitVenture/ui/images/UserManualPages/RegistrationPage.png?raw=true)

3. If the user went to the login page they can fill in their username and password in the fields, then press the login button, to get into the application.

![](https://github.com/P4FFos/FitVenture/blob/main/src/main/resources/fitVenture/ui/images/UserManualPages/LoginPage.png?raw=true)

4. Once the user logs in or registers to the application they go to the main dashboard, where they can see the chart showing Steps, Calories and Distance over a time interval. They can open achievements, goals, or challenges by pressing the button on the left page of the screen **(Red Box 1)**. They can also choose  from what time interval the chart should show **(Red Box 2)**. The user profile can be accessed by pressing the user profile button **(Red Box 3)**.

![](https://github.com/P4FFos/FitVenture/blob/main/src/main/resources/fitVenture/ui/images/UserManualPages/MainDashboardPage.png?raw=true)

5. If the user goes to the user profile page they can change their password and username **(Red Box 2)**. The users weight, height and full name can be also be edited by pressing the "Edit data" button **(Red Box 1)**

![](https://github.com/P4FFos/FitVenture/blob/main/src/main/resources/fitVenture/ui/images/UserManualPages/UserProfilePage.png?raw=true)

6. If the user opened the goals page they can see their completed goals for the past week or month period **(Red Box 3)**. They are also able to create and see either weight goals **(Red Box 1)** or running goals **(Red Box 2)**

![](https://github.com/P4FFos/FitVenture/blob/main/src/main/resources/fitVenture/ui/images/UserManualPages/GoalsPage.png?raw=true)

7. If the user goes to the daily challenges page they can see their steps, distance and calorie challenges for the day. **(img below)**

![](https://github.com/P4FFos/FitVenture/blob/main/src/main/resources/fitVenture/ui/images/UserManualPages/ChallendgesPage.png?raw=true)

8. If the user goes to the achievements page, they can see how close they are to completion. Light purple colour shows that achievement is not done yet and that the user has more work to do. When the achievement turns dark purple, the user has completed an achievement. 

![](https://github.com/P4FFos/FitVenture/blob/main/src/main/resources/fitVenture/ui/images/UserManualPages/AchievementPage.png?raw=true)







