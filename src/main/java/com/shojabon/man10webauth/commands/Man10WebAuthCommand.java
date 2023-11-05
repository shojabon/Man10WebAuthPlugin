package com.shojabon.man10webauth.commands;
import com.shojabon.man10webauth.Man10WebAuth;
import com.shojabon.man10webauth.commands.subCommands.*;
import com.shojabon.scommandrouter.SCommandRouter.SCommandObject;
import com.shojabon.scommandrouter.SCommandRouter.SCommandRouter;


public class Man10WebAuthCommand extends SCommandRouter {

    Man10WebAuth plugin;

    public Man10WebAuthCommand(Man10WebAuth plugin){
        super(plugin, "mwa");
        this.plugin = plugin;
        registerCommands();
        registerEvents();
    }

    public void registerEvents(){
        setNoPermissionEvent(e -> e.sender.sendMessage(Man10WebAuth.prefix + "§c§lあなたは権限がありません"));
        setOnNoCommandFoundEvent(e -> e.sender.sendMessage(Man10WebAuth.prefix + "§c§lコマンドが存在しません"));
    }

    public void registerCommands(){
        addCommand(
                new SCommandObject()
                        .prefix("register")
//                        .argument("パスワード")
                        .permission("man10webauth.register")
                        .explanation("アカウント登録")
                        .executor(new RegisterAccountCommand(plugin))
        );

        addCommand(
                new SCommandObject()
                        .prefix("reload")
                        .permission("man10webauth.reload")
                        .explanation(
                                "プラグインをリロードする",
                                "",
                                "設定を変更したときに使用する",
                                "コマンドを使用するとサーバー起動時状態に戻る"
                        )
                        .executor(new ReloadConfigCommand(plugin))
        );

        addCommand(
                new SCommandObject()
                        .prefix("api")
                        .permission("man10webauth.api")
                        .explanation(
                                "APIキーを発行する"
                        )
                        .executor(new APIKeyCommand(plugin))
        );

    }

}
