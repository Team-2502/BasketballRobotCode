package com.team2502.basketball;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DashboardData
{
    private DashboardData() {}

    static void update()
    {
        Example();
    }

    private static void Example()
    {
        SmartDashboard.putString("This is an example", "Example");
    }
}
