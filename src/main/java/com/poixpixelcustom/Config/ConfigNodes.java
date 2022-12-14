package com.poixpixelcustom.Config;

public enum ConfigNodes {
    VERSION_HEADER("version", "", ""),
    VERSION(
            "version.version",
            "",
            "# This is the current version of PoixpixelCustom. Please do not edit."),
    LAST_RUN_VERSION(
            "version.last_run_version",
            "",
            "# This is for showing the changelog on updates. Please do not edit."),

    INVITE_SYSTEM_CONFIRMATION_TIMEOUT(
            "invite_system.confirmation_timeout",
            "20",
            "",
            "# How many seconds before a confirmation times out for the receiver.",
            "# This is used for cost-confirmations and confirming important decisions."),

    INVITE_SYSTEM_CANCEL_COMMAND(
            "invite_system.cancel_command",
            "cancel",
            "",
            "# Command used to cancel some actions/tasks"),

    PLUGIN_USING_ESSENTIALS(
            "plugin.interfacing.using_essentials",
            "false",
            "",
            "# Enable using_essentials if you are using cooldowns in essentials for teleports."),

    INVITE_SYSTEM_CONFIRM_COMMAND(
            "invite_system.confirm_command",
            "confirm",
            "",
            "# Command used to confirm some PoixpixelCustom actions/tasks)");


    private final String Root;
    private final String Default;
    private String[] comments;

    ConfigNodes(String root, String def, String... comments) {

        this.Root = root;
        this.Default = def;
        this.comments = comments;
    }

    /**
     * Retrieves the root for a config option
     *
     * @return The root for a config option
     */
    public String getRoot() {

        return Root;
    }

    /**
     * Retrieves the default value for a config path
     *
     * @return The default value for a config path
     */
    public String getDefault() {

        return Default;
    }

    /**
     * Retrieves the comment for a config path
     *
     * @return The comments for a config path
     */
    public String[] getComments() {

        if (comments != null) {
            return comments;
        }

        String[] comments = new String[1];
        comments[0] = "";
        return comments;
    }
}
