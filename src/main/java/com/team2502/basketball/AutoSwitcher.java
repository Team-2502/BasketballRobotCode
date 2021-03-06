package com.team2502.basketball;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

class AutoSwitcher
{
    private static SendableChooser<AutoMode> autoChooser;

    public enum AutoMode
    {
        example("Example", null);

        private Class<? extends Command> autoCommand;
        private String name;

        AutoMode(String name, Class<? extends Command> autoCommand)
        {
            this.autoCommand = autoCommand;
            this.name = name;
        }

        public Command getInstance()
        {
            Command instance;
            try { instance = autoCommand.newInstance(); } catch(InstantiationException | IllegalAccessException e) { return null; }
            return instance;
        }
    }

    static void putToSmartDashboard()
    {
        autoChooser = new SendableChooser<AutoMode>();

        for(int i = 0; i < AutoMode.values().length; i++)
        {
            AutoMode mode = AutoMode.values()[ i ];
            if(i == 0) { autoChooser.addDefault(mode.name, mode); } else { autoChooser.addObject(mode.name, mode); }
        }

        SmartDashboard.putData("auto_modes", autoChooser);
    }

    static Command getAutoInstance() { return autoChooser.getSelected().getInstance(); }
}
