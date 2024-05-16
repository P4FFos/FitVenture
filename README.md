# FitVenture
![ ](https://git.chalmers.se/courses/dit113/2024/group-7/fitventure/-/raw/main/src/main/resources/fitVenture/ui/images/fitVentureLogo2.jpg?ref_type=heads)

# What is FitVenture (Purpose and Benefits)?

> FitVenture is a free-to-use application which was made to change the way people look at fitness. Through interactive features like weight and run goals, daily challenges, and the main feature achievements.

> Achievements visualise the comparison between the covered distance or calories with the length of either real world objects or daily calorie consumption of animals. FitVenture encourages people to be more active and improves their user experience. 

> FitVenture customers can create their own running and weight goals and keep track of their progress via the Bar Charts and Progress Bars. Additionally, user has opportunity to participate in daily challenges to get better motivation to exercise.

# Project demo

# Features?
- Completely FREE fitness application 
- MQTT based application 
- Uses publish-subscribe pattern
- Uses Embedded system for gathering data 
- Visualisation utilizing bar charts and progress bars 
- Goals, daily challenges and achievements 
- Functionality to change personal data  

# How it works ?
![ ](https://git.chalmers.se/courses/dit113/2024/group-7/fitventure/-/raw/47-readme-update/src/main/resources/fitVenture/ui/images/SystemDiagram.png?ref_type=heads)

### Software architecture
- User register, fills in personal information, which system saves into the JSON file 
- User login and goes to the main dashboard, where they can see their progress and statistics via the Bar Chart  
- On the main dashboard screen screen user can see the temperature & humidity values from the [sensor](https://wiki.seeedstudio.com/Grove-TemperatureAndHumidity_Sensor/) 
- Goals, daily challenges and achievements pages are accessible from the main dashboard 

### Hardware architecture

- [Arduino Wio Terminal](https://wiki.seeedstudio.com/Wio-Terminal-Getting-Started/) is connected to [three components](https://git.chalmers.se/courses/dit113/2024/group-7/fitventure/-/wikis/Components) via the 4-pin cabels 
- Temperature & humidity are visible on the [Arduino Wio Terminal](https://wiki.seeedstudio.com/Wio-Terminal-Getting-Started/) screen 
- XYZ Data from the [Accelerometer Sensor](https://wiki.seeedstudio.com/Grove-3-Axis_Digital_Accelerometer-1.5g/) is converted to the distance, which is used to calculate steps and calories burned
- [Button component](https://wiki.seeedstudio.com/Grove-Button/) is used to start/finish the exercise session
- The wio-terminal and PC are connected through the publish-subscribe pattern via the MQTT Broker 

# Main contribution 
_PS: Every group member wrote his personal opinion about the main contribution_

#### Danylo Baranov: 
> The PM for the period of the whole project, worked with git workflow organisation. Implemented login and registration page, JSON and project structure organisation, time chart for goals, start/end timer for the race (WIO, Back-end, MQTT), Folder structure fix (.gitignore, maven dependencies), worked with the bug fix of the publishing to the MQTT. Helped teammates with bug fixes throughout the project. 

#### Oleksandr Kozlov:
>  The initial UI and the logic behind User Profile, Goals Page and the complete redesign of the whole application UI towards the end of the project. Also, worked partially with the MQTT publish/subscribe of the user’s info and the sensor data.

#### Danis Music:
> Worked on the additional optional information in the registration page, the continuous integration pipelines for both the java application aswell as the arduino.  Worked on the achievements feature aswell. Fixed sourceDirectory issue aswell as created the content to .gitignore file

#### Abdullahi Mahamed:
> 

#### Jackson Niyomugabo:
> Ardunino's connection notification to the user, Progress visualization of data (Creation of Bar chart for day, week and month). Setting of weight goals and saving of the goals to the json file. Goals completion notification for both weight goal and running goals 

#### Stefan Tram:
> Mainly developed the data saving structure/logic and improved the way data is shown on charts. Contributed to main issues such as CI and Achievements. 


# Run the latest version 
` 1. Clone the repository: $ git clone https://git.chalmers.se/courses/dit113/2024/group-7/fitventure `

`2. Find the project directory: $ cd FitVenture/`

# User manual 
1. Once user open the application he goes to the registration-login page. If you don't have an account and have not used our application you can go the the registration page **(Red Box 1)**. In case you already used our application you can go to the login page **(Red Box 2)**
![ ](https://git.chalmers.se/courses/dit113/2024/group-7/fitventure/-/raw/47-readme-update/src/main/resources/fitVenture/ui/images/UserManualPages/LoginRegistrationPage.jpg?ref_type=heads)

2. Once you opened the registration page you can register by filling in the username and passoword **(Red Box 1)**. If you want to add an additional information you can press on "additional information button" and fill in the fields **(Red Box 2)**
![ ](https://git.chalmers.se/courses/dit113/2024/group-7/fitventure/-/raw/47-readme-update/src/main/resources/fitVenture/ui/images/UserManualPages/RegistrationPage.png?ref_type=heads)

3. If you went to the login page you can fill in the username and the password, than press the login button, to get into the application.
![](https://git.chalmers.se/courses/dit113/2024/group-7/fitventure/-/raw/47-readme-update/src/main/resources/fitVenture/ui/images/UserManualPages/LoginPage.png?ref_type=heads)

4. Once you login or register to the application you go to the main dashboard, where you can see the chart with the progress. You can open achievements, goals, or challenges by pressing the button on the left page of the screen **(Red Box 1)**. You also have an opportunity to choose which chart you want to look at **(Red Box 2)**. User profile can be accessed by pressing user profile button **(Red Box 3)**.
![](https://git.chalmers.se/courses/dit113/2024/group-7/fitventure/-/raw/47-readme-update/src/main/resources/fitVenture/ui/images/UserManualPages/MainDashboardPage.png?ref_type=heads)

5. If you got to the user profile page you can change your password and username **(Red Box 2)**. Your weight and height can be also changed, just press button "edit data" **(Red Box 1)**
![](https://git.chalmers.se/courses/dit113/2024/group-7/fitventure/-/raw/47-readme-update/src/main/resources/fitVenture/ui/images/UserManualPages/UserProfilePage.png?ref_type=heads)

6. If you opened goals page you can see the progress of your completed goals for the week and month period of time **(Red Box 3)**. You are also able to create and see either weight goals **(Red Box 1)** or running goals **(Red Box 2)**
![](https://git.chalmers.se/courses/dit113/2024/group-7/fitventure/-/raw/47-readme-update/src/main/resources/fitVenture/ui/images/UserManualPages/GoalsPage.png?ref_type=heads)

7. If you got into the daily challenges page you can see your steps, distance and calories challenges for today **(img below)**
![](https://git.chalmers.se/courses/dit113/2024/group-7/fitventure/-/raw/47-readme-update/src/main/resources/fitVenture/ui/images/UserManualPages/ChallendgesPage.png?ref_type=heads)

8. If you got into the achievements page, you can see progress of them. Light purple colour shows that achievement is not done yet and you should continue working. When the dark purple colour shows that you have completed an achievement 
![](https://git.chalmers.se/courses/dit113/2024/group-7/fitventure/-/raw/47-readme-update/src/main/resources/fitVenture/ui/images/UserManualPages/AchievementPage.png?ref_type=heads)







