# 🛡️ Husna - AntiCheat for Xray

**Husna** es un sistema anti-xray diseñado para ayudarte a detectar y gestionar xray en tu servidor. Actualmente en fase **Beta**, sin base de datos: los registros se eliminarán al reiniciar el servidor. La persistencia de datos será añadida en futuras versiones.
Puedes ayudar a **Husna** mejorando cosas haciendo un fork, y luego un pull request. **Husna** funciona como un AntiCheat, enviando alertas, ver el perfil, logs y permiso para bypassear.
**Husna** Es la mejor opcion en caso de que no quieras habilitar el Anti-XRay de tu spigot ya que causa muchos errores, confusiones, lag, etc.

---

## 📜 Permisos

| Permiso              | Descripción                                                          |
| -------------------- | -------------------------------------------------------------------- |
| `husna.gui.use`      | Permite abrir la interfaz gráfica (GUI) de Husna.                    |
| `husna.alerts.use`   | Permite activar o desactivar las alertas.                            |
| `husna.profile.view` | Permite ver el perfil detallado de un usuario.                       |
| `husna.use.reload`   | Permite recargar el plugin.                                          |
| `husna.logs.view`    | Permite ver los registros de minería de un usuario.                  |
| `husna.alert.bypass` | El jugador no generará alertas al minar. Ideal para administradores. |
| `husna.alert`        | Permite al staff ver las alertas generadas por posibles infractores. |

---

## 💻 Comandos

| Comando                    | Descripción                                   |
| -------------------------- | --------------------------------------------- |
| `/husna reload`            | Recarga la configuración del plugin.          |
| `/husna logs <usuario>`    | Muestra los registros de minería del usuario. |
| `/husna gui`               | Abre la interfaz gráfica del sistema Husna.   |
| `/husna profile <usuario>` | Muestra el perfil detallado del usuario.      |
| Subcomando: `/husna`       | Comando base para todos los subcomandos.      |

---

## ⚠️ Nota Importante (Versión Beta)

> **Husna aún no utiliza una base de datos.**
> Todos los logs se eliminan al reiniciar el servidor.
> Esta funcionalidad será mejorada en futuras actualizaciones.
