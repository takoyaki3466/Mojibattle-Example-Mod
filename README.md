# MojiBattle Example MOD

MojiBattleEXの公開APIとJSONを利用して、データ駆動キャラクターを追加する最小構成のサンプルMODです。

新しいJava製Abilityを実装せず、ゲームに用意されている既存のスキル・必殺技・パッシブを組み合わせてキャラクターを登録します。

## 追加されるキャラクター

### 例

| 項目 | 値 |
|---|---:|
| 最大HP | 900 |
| 基礎攻撃倍率 | 1.0 |
| 移動速度 | 6.0 |
| 当たり判定半径 | 0.6 |
| クリティカル | 無効 |

#### スキル：例示弾

「例」の文字を投射する遠距離攻撃です。

| 項目 | 値 |
|---|---:|
| ダメージ | 32 |
| クールダウン | 7秒 |
| 弾速 | 11 |
| 持続時間 | 2秒 |
| 弾の半径 | 0.45 |
| 命中時の必殺技ポイント | 12 |

#### 必殺技：例示回復

必殺技ポイントを消費して、自分のHPを回復します。

| 項目 | 値 |
|---|---:|
| 必要ポイント | 100 |
| 回復量 | 180 |
| 予告時間 | 0.2秒 |
| 演出時間 | 0.8秒 |

#### パッシブ：例示成長

30秒ごとに攻撃力が2%ずつ上昇します。攻撃力上昇は最大1.2倍まで蓄積します。

## 必要環境

- MojiBattleEX 0.1.0以上
- MojiBattle API 0.4.0
- JDK 21
- 標準コンテンツMOD

このMODは、標準コンテンツMODが提供する次の実装やリソースを使用します。

- `mojibattle:projectile`
- `mojibattle:self_heal`
- `mojibattle:compound_interest`
- `mojibattle:glyphs/a.png`
- 標準エフェクト

## 利用前の重要な修正

現在のソースコードでは、メタデータとエントリーポイントのMOD IDが一致していません。

- `mod.json`およびリソースの名前空間：`examplemod`
- `ExampleMod.java`の定数：`example_mod`

MojiBattleEXではこれらを完全に一致させる必要があります。  
`ExampleMod.java`を次のように修正してください。

```java
private static final String MOD_ID = "examplemod";
```

修正しない場合、MODの読み込みまたはキャラクター登録に失敗します。

## ビルド方法

### 1. MojiBattle APIをローカルMavenへ発行する

MojiBattleEX本体のプロジェクトディレクトリで実行します。

```powershell
.\gradlew.bat :api:publishToMavenLocal
```

このサンプルMODは次のAPIを参照します。

```text
jp.takoa.mojibattle:mojibattle-api:0.4.0
```

### 2. MODをビルドする

このプロジェクトのルートディレクトリで実行します。

```powershell
.\gradlew.bat clean jar
```

生成されるJAR：

```text
build/libs/mojibattle-example-mod-1.0.0.jar
```

MojiBattle APIはゲーム本体から提供されるため、MODのJARには同梱されません。

## 導入方法

生成されたJARを、MojiBattleEXの`mods`ディレクトリ直下へコピーします。

```powershell
Copy-Item `
  .\build\libs\mojibattle-example-mod-1.0.0.jar `
  C:\path\to\MojiBattleEX\mods\
```

配置例：

```text
MojiBattleEX/
├─ mods/
│  ├─ mojibattle-<version>.jar
│  └─ mojibattle-example-mod-1.0.0.jar
└─ ...
```

JARを配置したあと、MojiBattleEXを起動してください。

独自の起動構成では、JVMシステムプロパティ`mojibattle.modsDir`を使ってMODディレクトリを変更できます。

## プロジェクト構成

```text
MojiBattleEX-exampleMod/
├─ build.gradle.kts
├─ settings.gradle.kts
└─ src/main/
   ├─ java/
   │  └─ com/example/mojibattle/
   │     └─ ExampleMod.java
   └─ resources/
      ├─ META-INF/mojibattle/
      │  └─ mod.json
      ├─ assets/examplemod/
      │  └─ i18n/
      │     └─ ja_jp.json
      └─ data/examplemod/
         ├─ characters/
         │  └─ example.json
         └─ abilities/
            ├─ example_skill.json
            ├─ example_ultimate.json
            └─ example_passive.json
```

## 各ファイルの役割

### `mod.json`

MOD ID、バージョン、対応ゲームバージョン、エントリーポイント、依存関係を定義します。

### `ExampleMod.java`

`GameMod`を実装するMODのエントリーポイントです。

`register`メソッドから、キャラクターと3種類のAbility JSONをゲームへ登録します。

### `characters/example.json`

キャラクターのHP、移動速度、当たり判定、使用するスキルなどを定義します。

### `abilities/example_skill.json`

通常スキルのダメージ、クールダウン、弾速などを定義します。

### `abilities/example_ultimate.json`

必殺技のポイント消費量や回復量を定義します。

### `abilities/example_passive.json`

時間経過による攻撃力上昇を定義します。

### `i18n/ja_jp.json`

キャラクター名、Ability名、説明文などの日本語テキストを定義します。

## カスタマイズ

キャラクター性能は、主に次のJSONを編集して変更できます。

```text
src/main/resources/data/examplemod/characters/example.json
src/main/resources/data/examplemod/abilities/example_skill.json
src/main/resources/data/examplemod/abilities/example_ultimate.json
src/main/resources/data/examplemod/abilities/example_passive.json
```

表示名や説明文は次のファイルで変更できます。

```text
src/main/resources/assets/examplemod/i18n/ja_jp.json
```

MOD IDを変更する場合は、次の箇所をすべて同じ値にしてください。

- `mod.json`の`id`
- `ExampleMod#id()`が返す値
- `ResourceId`の名前空間
- `assets/<modid>/`
- `data/<modid>/`
- 各JSON内のID
- 翻訳キー

MOD IDには小文字の英数字と`_`、`.`、`-`を使用できます。

## トラブルシューティング

### APIが見つからない

次のようなエラーが発生する場合は、MojiBattle API 0.4.0がローカルMavenへ発行されているか確認してください。

```text
Could not find jp.takoa.mojibattle:mojibattle-api:0.4.0
```

MojiBattleEX本体のプロジェクトで次を実行します。

```powershell
.\gradlew.bat :api:publishToMavenLocal
```

### MODが検出されない

次を確認してください。

- JARが`mods`ディレクトリの直下にある
- 拡張子が`.jar`になっている
- JAR内に`META-INF/mojibattle/mod.json`が存在する
- `mod.json`の必須項目がすべて設定されている
- 対応するゲームバージョンを使用している

### キャラクター登録に失敗する

次を確認してください。

- `ExampleMod#id()`と`mod.json`の`id`が一致している
- リソースの名前空間がMOD IDと一致している
- 各JSONから参照しているIDが存在する
- 標準コンテンツMODが一緒に導入されている
- 同じMOD IDのJARを褤数配置していない

## 注意事項

MojiBattleEXのMODは、ゲーム本体と同じOS権限で実行されます。内容を確認できないJARや、信頼できない配布元のMODは導入しないでください。

このプロジェクトは、MojiBattleEXのデータ駆動キャラクターMODを作成するためのサンプルです。
