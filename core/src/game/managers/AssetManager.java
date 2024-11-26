package game.managers;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import utilities.FilePaths;

public class AssetManager extends com.badlogic.gdx.assets.AssetManager {
    public AssetManager() {
        super();
        setLoader(TiledMap.class, new TmxMapLoader());

        load("imgs/backgrounds/backgroundsAux.png", Texture.class);
        load("imgs/backgrounds/deathScreen.png", Texture.class);
        load("imgs/backgrounds/loadingScreen.png", Texture.class);
        load("imgs/backgrounds/winScreen.png", Texture.class);

        load("imgs/characters/adel/adel0.png", Texture.class);
        load("imgs/characters/adel/adel1.png", Texture.class);
        load("imgs/characters/adel/adel2.png", Texture.class);
        load("imgs/characters/adel/adelDiamond.png", Texture.class);
        load("imgs/characters/adel/blackBullet.png", Texture.class);
        load("imgs/characters/adel/bullet.png", Texture.class);
        load("imgs/characters/adel/bulletGreen.png", Texture.class);
        load("imgs/characters/adel/bulletRed.png", Texture.class);

        load("imgs/characters/deadEye/deadEye.png", Texture.class);
        load("imgs/characters/deadEye/bullet.png", Texture.class);

        load("imgs/characters/shopKeeper/shopKeeper.png", Texture.class);

        load("imgs/characters/skeleton/bullet.png", Texture.class);
        load("imgs/characters/skeleton/skeleton.png", Texture.class);

        load("imgs/icons/adel.png", Texture.class);
        load("imgs/icons/shop.png", Texture.class);
        load("imgs/icons/skull.png", Texture.class);

        load("imgs/items/candy/candy.png", Texture.class);
        load("imgs/items/randomcard/randomCard.png", Texture.class);
        load("imgs/items/upCard/upCard.png", Texture.class);
        load("imgs/items/bomb.png", Texture.class);
        load("imgs/items/candy.png", Texture.class);
        load("imgs/items/cap.png", Texture.class);
        load("imgs/items/diamond.png", Texture.class);
        load("imgs/items/mushroom.png", Texture.class);
        load("imgs/items/randomCard.png", Texture.class);
        load("imgs/items/shell.png", Texture.class);
        load("imgs/items/skeletonMask.png", Texture.class);
        load("imgs/items/upCard.png", Texture.class);

        load(FilePaths.ROOMS + "boss/boss_1.tmx", TiledMap.class);
        load(FilePaths.ROOMS + "shop/shop.tmx", TiledMap.class);
        load(FilePaths.ROOMS + "stone/map_1.tmx", TiledMap.class);
        load(FilePaths.ROOMS + "stone/map_2.tmx", TiledMap.class);

        load(FilePaths.AUDIO + "game/music/Shop.mp3", Music.class);
        load(FilePaths.AUDIO + "game/music/ShopIntro.mp3", Music.class);
        load(FilePaths.AUDIO + "game/music/Undead.mp3", Music.class);
        load(FilePaths.AUDIO + "game/music/UndeadIntro.mp3", Music.class);
        load(FilePaths.AUDIO + "game/death.mp3", Music.class);
        load(FilePaths.AUDIO + "game/shoot.mp3", Music.class);
        load(FilePaths.AUDIO + "clickSound.mp3", Music.class);
        load(FilePaths.AUDIO + "hoverSound.mp3", Music.class);
        load(FilePaths.AUDIO + "menuMusic.mp3", Music.class);
        load(FilePaths.AUDIO + "typing.mp3", Music.class);

        finishLoading();
    }
}
