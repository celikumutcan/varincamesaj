# VarıncaMesaj 📍💬

VarıncaMesaj, belirlediğiniz bir konuma yaklaştığınızda WhatsApp üzerinden otomatik olarak önceden belirlediğiniz bir mesajı gönderen bir Android otomasyon uygulamasıdır.

## 💡 Projenin Hikayesi
Bu projeyi geliştirmemdeki temel motivasyon, şirketten eve dönerken servisteyken eve son 10 dakika kala abime haber vermek için sürekli telefona bakma ve mesaj atma zahmetinden kurtulmaktı. Uygulama sayesinde artık belirlediğim konuma girdiğim an sistem benim yerime "Geliyorum!" mesajını atıyor. :)

## 🚀 Özellikler
- **Geofencing API:** Pil dostu arka plan konum takibi sayesinde belirlenen enlem ve boylama (örneğin eve 10 dk uzaklıktaki bir noktaya) girildiğini anında tespit eder.
- **Accessibility Service (Erişilebilirlik):** Konuma varıldığında WhatsApp'ı açar ve Gönder butonuna insan gibi otomatik tıklayarak mesajı iletir.
- **Dinamik UI:** Gönderilecek telefon numarasını, mesaj metnini ve hedef koordinatlarını uygulama içinden kolayca değiştirip kaydedebilirsiniz (SharedPreferences).

## 🛠 Kullanım
1. Uygulamayı açın ve gerekli konum izinlerini verin.
2. Ayarlar > Erişilebilirlik menüsünden `VarincaMesaj` servisini aktif hale getirin.
3. Hedef koordinatları, numarayı ve mesajı girip **"Başlat"** butonuna basın.

---
*Not: Bu proje eğitim ve kişisel otomasyon amacıyla geliştirilmiş olup Android'in arka plan servisleri ve erişilebilirlik yeteneklerini test etmek için harika bir örnektir.*
