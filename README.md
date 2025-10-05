<div align="center">

<a href="https://modrinth.com/plugin/kernel/" target="_blank" title="Kernel on Modrinth">
  <img width="160px" alt="Kernel Icon" src="https://cdn.modrinth.com/data/cached_images/86832cb77c5fbd391f7c7018f3e8369baf08ed9c.png">
</a>

<a name="readme-top"></a>

# Kernel

**A powerful core plugin delivering essential commands and critical features to ensure a smooth and stable server
experience.**

![Minecraft Version][minecraft_version_img]
![Downloads][downloads_img]
[![Releases][releases_img]][releases_url]
[![License][repo_license_img]][repo_license_url]

</div>

---

## ✨ Features

- Robust foundation for other plugins
- Essential server commands
- Performance-oriented design
- Easy configuration
- Lightweight and optimized
- Built for Minecraft 1.21+

---

## 📦 Installation

1. Download the latest release from [Modrinth](https://modrinth.com/project/kernel#download)
   or [GitHub Releases][releases_url].
2. Drop the `.jar` file into your server’s `plugins/` folder.
3. Reload the server and configure as needed.

---

## 📁 Configuration

Kernel comes with a simple and clean config file located at:

<details>
<summary>/plugins/Kernel/config.yml</summary>

```yaml
## Kernel Configuration
```

</details>

> You can adjust permissions, enable/disable features, and more.

---

## 🧩 Compatibility

Kernel is designed to work seamlessly with:

* Spigot and Paper
* Java 21+
* Most major plugins

---

## 🛠️ Commands & Permissions

<details>
<summary>Commands & Permissions</summary>

| Command                | Description                                   | Permission                   |
|------------------------|-----------------------------------------------|------------------------------|
| `/kernel`              | Main command info/help.                       | `kernel.command.main`        |
| `/kernel help`         | Commands list.                                | `kernel.command.main.help`   |
| `/kernel reload`       | Reloads configuration.                        | `kernel.command.main.reload` |
| `/heal [player]`       | Heals a player to full health.                | `kernel.admin.heal`          |
| `/feed [player]`       | Fills a player's hunger to max.               | `kernel.admin.feed`          |
| `/fly [player]`        | Toggles flight mode.                          | `kernel.admin.fly`           |
| `/vanish [player]`     | Toggles vanish mode.                          | `kernel.admin.vanish`        |
| `/god [player]`        | Toggles god mode.                             | `kernel.admin.god`           |
| `/hat [player]`        | Puts the item in your hand on your head.      | `kernel.admin.hat`           |
| `/whois <player>`      | Displays detailed information about a player. | `kernel.admin.whois`         |
| `/broadcast <message>` | Sends a message to the entire server.         | `kernel.admin.broadcast`     |
| `/adminchat <msg>`     | Sends a message to admin chat.                | `kernel.admin.adminchat`     |
| `/freeze [player]`     | Freezes a player, preventing all actions.     | `kernel.admin.freeze`        |
| `/clearchat`           | Clears chat for all players.                  | `kernel.admin.clearchat`     |
| `/invsee <player>`     | Views another player's inventory.             | `kernel.admin.invsee`        |
| `/equsee <player>`     | Views another player's armors and offhand.    | `kernel.admin.equsee`        |
| `/endersee [player]`   | Views another player's ender chest.           | `kernel.admin.endersee`      |
| `/ping [player]`       | Checks connection latency.                    | `kernel.utility.ping`        |
| `/playtime [player]`   | Shows total playtime.                         | `kernel.utility.playtime`    |
| `/seen <player>`       | Shows when a player was last online.          | `kernel.utility.seen`        |
| `/afk`                 | Toggles AFK status.                           | `kernel.utility.afk`         |

</details>

---

## 📃 License

This project is licensed under the [All Rights Reserved license][repo_license_url].

---

[downloads_img]: https://img.shields.io/modrinth/dt/kernel?color=default

[releases_img]: https://img.shields.io/github/v/release/Lightre/kernel?color=aqua

[releases_url]: https://github.com/Lightre/kernel/releases

[repo_license_img]: https://img.shields.io/badge/license-ARR-yellow.svg

[repo_license_url]: https://github.com/Lightre/kernel/blob/main/LICENSE

[minecraft_version_img]: https://img.shields.io/badge/minecraft-1.21x-green.svg