package com.example.addon.modules;

import com.example.addon.AddonTemplate;
import meteordevelopment.meteorclient.events.game.ReceiveMessageEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;

public class ModuleExample extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<String> password = sgGeneral.add(new StringSetting.Builder()
        .name("password")
        .description("Mật khẩu tài khoản.")
        .defaultValue("123456")
        .build()
    );

    private final Setting<Boolean> autoSelectSmp = sgGeneral.add(new BoolSetting.Builder()
        .name("auto-select-smp")
        .description("Tự động vào SMP.")
        .defaultValue(true)
        .build()
    );

    private final Setting<String> smpCommand = sgGeneral.add(new StringSetting.Builder()
        .name("smp-command")
        .description("Lệnh chọn server con.")
        .defaultValue("smp")
        .build()
    );

    private final Setting<Integer> delay = sgGeneral.add(new IntSetting.Builder()
        .name("delay-ms")
        .description("Độ trễ gửi lệnh (ms).")
        .defaultValue(1000)
        .min(0)
        .sliderMax(5000)
        .build()
    );

    public ModuleExample() {
        super(AddonTemplate.CATEGORY, "auto-login-smp", "Tự động đăng nhập và chọn cụm SMP.");
    }

    @EventHandler
    private void onReceiveMessage(ReceiveMessageEvent event) {
        if (mc.player == null) return;
        String msg = event.getMessage().getString().toLowerCase();

        if (msg.contains("/login") || msg.contains("dang nhap") || msg.contains("đăng nhập")) {
            sendCommand("login " + password.get(), delay.get());
        } 
        
        if (autoSelectSmp.get() && (msg.contains("thành công") || msg.contains("success") || msg.contains("chào mừng"))) {
            sendCommand(smpCommand.get(), delay.get() + 500); 
        }
    }

    private void sendCommand(String command, int waitTime) {
        new Thread(() -> {
            try {
                Thread.sleep(waitTime);
                if (mc.player != null) {
                    mc.player.networkHandler.sendChatCommand(command);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
