package com.poixpixelcustom.Commands;

import com.poixpixelcustom.PoixpixelCustomSettings;
import com.poixpixelcustom.util.ChatTools;
import com.poixpixelcustom.util.Colors;
import org.bukkit.command.CommandSender;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum HelpMenu {
    GENERAL_HELP {
        protected MenuBuilder load() {
            return new MenuBuilder()
                    .addTitle("help_0")
                    .add("help_1")
                    .add(ChatTools.formatCommand("/enrich", "?", ""));
        }
    },

    HELP {
        public MenuBuilder load() {
            return new MenuBuilder("PoixpixelCustom", "General help for PoixpixelCustom");
        }
    };

    private static class MenuBuilder {
        final List<String> lines = new ArrayList<>();
        private String command;
        String requirement = "";

        MenuBuilder(String cmd, boolean cmdTitle) {
            this.command = cmd;
            if (cmdTitle)
                this.lines.add(ChatTools.formatTitle("/" + command));
        }

        MenuBuilder(String cmd) {
            this(cmd, true);
        }

        MenuBuilder(String cmd, String desc) {
            this(cmd);
            if (!desc.isEmpty())
                add("", desc);
        }

        MenuBuilder(String cmd, String requirement, String desc) {
            this(cmd);
            this.requirement = requirement;
            if (!desc.isEmpty())
                add("", desc);
        }

        MenuBuilder() {
            this.command = "";
        }

        MenuBuilder add(String subCmd, String desc) {
            return add(this.requirement, subCmd, desc);
        }

        MenuBuilder add(String requirement, String subCmd, String desc) {
            this.lines.add(ChatTools.formatCommand(requirement, "/" + command, subCmd, desc));
            return this;
        }

        MenuBuilder add(String requirement, String command, String subCmd, String desc) {
            this.lines.add(ChatTools.formatCommand(requirement, command, subCmd, desc));
            return this;
        }

        MenuBuilder add(String line) {
            this.lines.add(line);
            return this;
        }

        MenuBuilder addTitle(String title) {
            this.lines.add(ChatTools.formatTitle(title));
            return this;
        }

        MenuBuilder addCmd(String cmd, String subCmd, String desc) {
            return add(requirement, cmd, subCmd, desc);
        }
    }
}
