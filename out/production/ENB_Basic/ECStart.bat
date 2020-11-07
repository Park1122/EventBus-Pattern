%ECHO OFF
%ECHO Starting ECS System
PAUSE
%ECHO ECS Monitoring Output Console
START "COURSE REGISTRATION SYSTEM OUTPUT CONSOLE" /NORMAL java Components.ClientOutput.ClientOutputMain
%ECHO ECS Monitoring Input Console
START "COURSE REGISTRATION SYSTEM INPUT CONSOLE" /NORMAL java Components.ClientInput.ClientInputMain
%ECHO Starting Student Component Function Console
START "STUDENT COMPONENT FUNCTION CONSOLE" /NORMAL java Components.Student.StudentMain
%ECHO Starting Course Component Function Console
START "COURSE COMPONENT FUNCTION CONSOLE" /NORMAL java Components.Course.CourseMain
%ECHO Starting Apply Component Function Console
START "APPLY COMPONENT FUNCTION CONSOLE" /NORMAL java Components.Register.RegisterMain