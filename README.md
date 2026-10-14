# Android Auto File Transfer

A mobile-first progressive web app with a colorful custom logo, glass-style interface, and browser-based peer-to-peer file transfer.

## Upload this ZIP to GitHub Pages
1. Download and extract the ZIP on your phone.
2. Open your repository: https://github.com/krishlohar1-sketch/Android-auto-file-transfer-
3. Upload the extracted files and folders to the repository root, replacing files with the same names.
4. Commit the changes and wait for GitHub Pages to publish.
5. Open the live HTTPS site on both phones: https://krishlohar1-sketch.github.io/Android-auto-file-transfer-/

## Test transfer
1. Both phones need internet and the page must stay open.
2. On phone A, tap **Start sender** and share the connection ID.
3. On phone B, enter the ID under **Receive files** and tap **Connect to sender**.
4. Once connected, phone A selects a small photo and taps **Send selected files**.
5. On phone B, tap the download link when the file arrives.

## Important honest limitations
- This is a browser/PWA, not a native Android app. It cannot automatically turn Wi-Fi, hotspot, Bluetooth, or permissions on/off.
- PeerJS uses a public signaling service and WebRTC; networks/firewalls may prevent a connection. Same Wi-Fi can help.
- QR displays the sender ID, but automatic QR scanning is not implemented; receiver must enter/paste the ID.
- PeerJS and QR libraries load from public CDNs, so first use needs internet. Cached page shell does not enable offline pairing.
- Large files can be slow or run into browser memory limits. Start with small files.
- The package has not been verified with a live two-physical-phone test here. Test with a small non-sensitive file before relying on it.
