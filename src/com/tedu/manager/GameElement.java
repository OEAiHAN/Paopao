package com.tedu.manager;

public enum GameElement {
    /**
     * MAPS 地图
     * PAOPAO 泡泡
     * TOOL 道具
     * PLAY 玩家
     * ENEMY 敌人
     * DIE 死亡效果
     * EXPLODE 泡泡爆炸
     * WEAKMAPS 可破坏地图，未实体，用于AI判定
     * FLOOR 空地图，未实体，用于AI判定
     */
    MAPS, PAOPAO, TOOL, PLAYER, ENEMY, DIE, EXPLODE, WEAKMAPS, FLOOR;
    // 枚举类型的顺序是声明的顺序
    // 我们定义的枚举类型，在编译的时候，虚拟机会自动帮助生成class文件，
    // 并会加载很多代码和方法，例如默认私有构造函数
}
