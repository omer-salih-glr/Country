# Telefon Numarası Ülke kodu Seçme Modülü.

## Kurulum
- `phoneCountryCodepicker` klasörünü olduğu gibi kopyalayın ve projenizin kök kasörü içerisine
  yapıştırın.
- Kök projenizdeki `settings.gradle` dosyasının sonuna işaretli satırı ekleyin.
    - ```
    rootProject.name = ...
    include ':app'
    include ':phoneCountryCodepicker' //
    ```
- Projenizdeki `build.gradle(app)` dosyasını açın ve aşağıdaki satırı projenize ekleyin.
  - ```
    dependencies {
      ...
      ...
      ...
      implementation project(":phoneCountryCodepicker")
    }
    ```
- Kök projenize ait `AndroidManifest.xml` dosyasını açın. Aşağıdaki satırı application tag'ı içerisine ekleyin.
  - ```
    <application
          ...
          ...
          tools:replace="android:theme">
    ```
- İşlem tamam! Umarım hata almamışsındır :P

### Son olarak

phoneCountryCodePicker içerisindeki themes.xml dosyasında colorPrimary ve colorSecondary renklerini
mevcut projenin renkleri ile değiştirmelisin. Bu sayede edittext seçildiği zamanki renkleri ve diğer
animasyon renkleri uygulama ile aynı olur.


## Kullanım
``CountryPickerActivity`` startActivityForResult() ile başlatılmalıdır. Herhangi bir ülke seçildiğinde intent içerisine aşağıdaki sınıfı JSON String olarak ekler.
```
data class CountryListModelItem(
        val code: String,
        val dialCode: String,
        val name: String
)
```
# Fragment içerisinde örnek kullanım
```
class PhoneNumberFragment : Fragment() {

    private lateinit var binding: FragmentPhoneNumberBinding
    private lateinit var currentCountry: CountryListModel.CountryListModelItem

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        this::onCountryPicked
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhoneNumberBinding.inflate(inflater, container, false)
        currentCountry = try {
            CountryListUtils.getCountryList(requireContext()).find {
                it.code.lowercase().equals(Locale.getDefault().country, true)
            }!!
        } catch (e: Exception) { // Let's make it more failsafe.
            CountryListModel.CountryListModelItem("US", "+1", "USA")
        }
        updateUI()
        setOnClickListeners()
        return binding.root
    }

    private fun updateUI() {
        binding.tphoneCountryCode.text = currentCountry.dialCode
        CountryListUtils.loadCountryImage(currentCountry.code, binding.ivSelectedPicture)
    }

    private fun setOnClickListeners() {
        binding.ivSelectedPicture.setOnClickListener {
            launcher.launch(Intent(requireContext(), CountryPickerActivity::class.java))
        }
    }

    private fun onCountryPicked(result: ActivityResult) {
        if (result.resultCode != 0 || result.data?.getStringExtra("data") == null) return
        currentCountry = Gson().fromJson(
            result.data!!.getStringExtra("data"),
            CountryListModel.CountryListModelItem::class.java
        )
        updateUI()
    }
}
```
#### Emirhan KOLVER | 23.09.2022 | Stable Mobile ©