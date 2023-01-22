package com.shojabon.man10webauth.commands;
import com.shojabon.man10webauth.Man10WebAuth;
import com.shojabon.man10webauth.commands.subCommands.*;
import com.shojabon.mcutils.Utils.SCommandRouter.SCommandArgument;
import com.shojabon.mcutils.Utils.SCommandRouter.SCommandArgumentType;
import com.shojabon.mcutils.Utils.SCommandRouter.SCommandObject;
import com.shojabon.mcutils.Utils.SCommandRouter.SCommandRouter;


public class Man10WebAuthCommand extends SCommandRouter {

    Man10WebAuth plugin;

    public Man10WebAuthCommand(Man10WebAuth plugin){
        this.plugin = plugin;
        registerCommands();
        registerEvents();
    }

    public void registerEvents(){
        setNoPermissionEvent(e -> e.sender.sendMessage(Man10WebAuth.prefix + "§c§lあなたは権限がありません"));
        setOnNoCommandFoundEvent(e -> e.sender.sendMessage(Man10WebAuth.prefix + "§c§lコマンドが存在しません"));
    }

    public void registerCommands(){
        //shops command
        addCommand(
                new SCommandObject()
                        .addArgument(new SCommandArgument().addAllowedString("test")).

                        addRequiredPermission("man10shopv3.test").addExplanation("テスト").
                        setExecutor(new TestCommand(plugin))
        );

        addCommand(
                new SCommandObject()
                        .addArgument(new SCommandArgument().addAllowedString("register"))
                        .addArgument(new SCommandArgument().addAlias("パスワード"))

                        .addRequiredPermission("man10webauth.register").addExplanation("アカウント登録").
                        setExecutor(new RegisterAccountCommand(plugin))
        );

        addCommand(
                new SCommandObject()
                        .addArgument(new SCommandArgument().addAllowedString("set-password"))
                        .addArgument(new SCommandArgument().addAlias("パスワード"))

                        .addRequiredPermission("man10webauth.set.password").addExplanation("アカウントのパスワードを変更").
                        setExecutor(new ChangePasswordCommand(plugin))
        );

        addCommand(
                new SCommandObject()
                        .addArgument(new SCommandArgument().addAllowedString("set-username"))
                        .addArgument(new SCommandArgument().addAlias("パスワード"))

                        .addRequiredPermission("man10webauth.set.username").addExplanation("アカウントのユーザー名を変更").
                        setExecutor(new ChangeUsernameCommand(plugin))
        );
        addCommand(
                new SCommandObject()
                        .addArgument(new SCommandArgument().addAllowedString("reload")).

                        addRequiredPermission("man10webauth.reload")
                        .addExplanation("プラグインをリロードする")
                        .addExplanation("")
                        .addExplanation("設定を変更したときに使用する")
                        .addExplanation("コマンドを使用するとサーバー起動時状態に戻る")
                        .setExecutor(new ReloadConfigCommand(plugin))
        );
    }

}
