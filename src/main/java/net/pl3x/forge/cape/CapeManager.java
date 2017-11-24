package net.pl3x.forge.cape;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.BannerTextures;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.ResourceLocation;

import java.io.File;
import java.util.HashMap;

public class CapeManager {
    public static ResourceLocation CAPE_DIR;
    private static HashMap<String, Object[]> capeList = new HashMap<>();

    public static void addCape(String username, String url) {
        capeList.put(username, new Object[]{url, null});
    }

    public static void addCape(String username, ItemStack stack) {
        if (stack.getItem() == Items.BANNER) {
            capeList.put(username, new Object[]{"banner", new CapeBannerData(username, stack)});
        } else {
            capeList.remove(username);
        }
    }

    public static Object[] getCape(String username) {
        return capeList.get(username);
    }

    public static void tick() {
        try {
            Minecraft MC = Minecraft.getMinecraft();
            if (MC.world != null) {
                for (int i = 0; i < MC.world.playerEntities.size(); i++) {
                    AbstractClientPlayer player = (AbstractClientPlayer) MC.world.playerEntities.get(i);
                    NetworkPlayerInfo info = player.getPlayerInfo();

                    String username = player.getDisplayNameString();
                    Object[] data = capeList.get(username);
                    if (data != null && data.length > 0) {
                        String url = (String) data[0];
                        CapeData capedata = (CapeData) data[1];
                        if (url != null && !url.isEmpty() && capedata == null) {
                            capedata = new CapeData(username, url);
                            capeList.put(username, new Object[]{url, capedata});
                            capedata.setDaemon(true);
                            capedata.setName("Cape Download: " + url);
                            capedata.start();
                        }
                        if (info != null && !info.playerTextures.containsKey(MinecraftProfileTexture.Type.CAPE)) {
                            if (capedata != null && capedata.getCapeLocation() != null) {
                                info.playerTextures.put(MinecraftProfileTexture.Type.CAPE, capedata.getCapeLocation());
                            }
                        }
                    } else if (info != null) {
                        info.playerTextures.remove(MinecraftProfileTexture.Type.CAPE);
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static class CapeBannerData extends CapeData {
        private ItemStack stack;

        public CapeBannerData(String username, ItemStack banner) {
            super(username, "");
            this.stack = banner;
            capeLocation = null;
        }

        public void run() {
        }

        public ResourceLocation getCapeLocation() {
            if (capeLocation == null) {
                TileEntityBanner te = new TileEntityBanner();
                te.setItemValues(stack, true);
                capeLocation = BannerTextures.CAPE_DESIGNS.getResourceLocation(te.getPatternResourceLocation(), te.getPatternList(), te.getColorList());
            }
            return capeLocation;
        }

        public ThreadDownloadImageData getCapeImage() {
            return null;
        }

        public String getUrl() {
            return null;
        }
    }

    public static class CapeData extends Thread {
        ResourceLocation capeLocation;
        private ThreadDownloadImageData capeImage;
        private String username;
        private String url;

        public CapeData(String username, String url) {
            this.username = username;
            this.url = url;
        }

        public void run() {
            try {
                downloadCape();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        private void downloadCape() {
            capeLocation = new ResourceLocation(CAPE_DIR + username);
            capeImage = CapeDownloader.DownloadImageThread(url, capeLocation);
        }

        public ResourceLocation getCapeLocation() {
            return capeLocation;
        }

        public ThreadDownloadImageData getCapeImage() {
            return capeImage;
        }

        public String getUrl() {
            return url;
        }
    }

    private static class CapeDownloader {
        private static ThreadDownloadImageData DownloadImageThread(String url, ResourceLocation rl) {
            try {
                File capeFile = new File(rl.getResourcePath() + ".png");
                if (capeFile.exists()) {
                    capeFile.delete();
                }
                capeFile.deleteOnExit();
                TextureManager var4 = Minecraft.getMinecraft().getTextureManager();
                Object var5 = var4.getTexture(rl);
                var5 = new ThreadDownloadImageData(capeFile, url, null, null);

                var4.loadTexture(rl, (ITextureObject) var5);
                return (ThreadDownloadImageData) var5;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
