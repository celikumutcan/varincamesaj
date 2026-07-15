# VarıncaMesaj 📍💬

VarıncaMesaj, belirlediğiniz bir konuma yaklaştığınızda WhatsApp üzerinden otomatik olarak önceden belirlediğiniz bir mesajı gönderen bir Android otomasyon uygulamasıdır.

## 💡 Projenin Hikayesi
Bu projeyi, servis gibi ulaşım araçlarında belirlediğim konuma yaklaştığımda WhatsApp üzerinden otomatik mesaj göndererek, sürekli telefona bakma ve manuel mesaj atma zahmetinden kurtulmak amacıyla geliştirdim.

## 🚀 Özellikler
- **Geofencing API:** Pil dostu arka plan konum takibi sayesinde belirlenen enlem ve boylama (örneğin eve 10 dk uzaklıktaki bir noktaya) girildiğini anında tespit eder.
- **Accessibility Service (Erişilebilirlik):** Konuma varıldığında WhatsApp'ı açar ve Gönder butonuna insan gibi otomatik tıklayarak mesajı iletir.
- **Dinamik UI:** Gönderilecek telefon numarasını, mesaj metnini ve hedef koordinatlarını uygulama içinden kolayca değiştirip kaydedebilirsiniz (SharedPreferences).

## 🛠 Kullanım
1. Uygulamayı açın ve gerekli konum izinlerini verin.
2. Ayarlar > Erişilebilirlik menüsünden `VarincaMesaj` servisini aktif hale getirin.
3. Hedef koordinatları, numarayı ve mesajı girip **"Başlat"** butonuna basın.

---
*Not: Bu proje eğitim ve kişisel otomasyon amacıyla geliştirilmiş olup Android'in arka plan servisleri ve erişilebilirlik yeteneklerini test etmek için örnektir.*
