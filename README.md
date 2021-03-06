Pokemon
======
<img src="https://user-images.githubusercontent.com/90249177/148031177-748fb2dc-1626-42b5-aad4-543357d7d010.png" width="100%" height="60%" title="Pokemon" alt="Pokemon"></img>

## Abstract

 ### - 세 개의 탭을 가진 간단한 안드로이드 앱을 구현하였습니다.</p>
 Tap 1: 휴대폰의 연락처 데이터를 불러와 RecyclerView로 보여준다. </p>
 Tap 2: 갤러리에서 이미지 데이터를 불러와 PhotoView로 보여준다.</p>
 Tap 3: 만보기 기능을 구현한다.</p>

## Features
<ul>
 <li><h2>Contact</h2></li>
 <ul>
  <li>스마트폰에 있는 연락처에서 정보를 가져와 recyclerview로 목록을 구성합니다. 메인화면에서는 포켓몬이Contact 화면에서는 연락처 목록의 이름을 확인할 수 있으며 바로 통화가 가능합니다. 오른쪽 아래에 +버튼을 누르면 전화번호 추가 창이 나와 연락처를 추가 저장할 수 있습니다. 새로고침을 하면 새롭게 저장된 정보도 바로 확인이 가능합니다. 각 목록을 클릭하면 새로운 창이 나와 연락처의 프로필 사진과 이름, 연락처를 확인할 수 있습니다. 추가로 CALL, MESSAGE, SHARE를 클릭하면 바로 통화, 문자, 연락처 공유가 가능합니다. 연락처를 최신 상태로 유지하여 포켓몬스터 친구들의 연락처를 가져보세요!</li>
 </ul>
 <li><h2>Gallery</h2></li>
 <ul>
  <li>스마트폰에 있는 갤러리에서 정보를 가져와 이미지를 보여줍니다. Gallery 화면에서는 많은 이미지들을 한번에 쉽게 확인할 수 있습니다. 오른쪽 아래에 있는 카메라 버튼을 클릭하면 카메라로 이동하게 되며 촬영을 통해 사진을 갤러리에 저장할 수 있습니다. 찍은 사진을 저장하지 않고 다시 찍고 싶다면 다시 시도 버튼을 누르면 됩니다. 이후 새로 고침 과정을 통해 방금 찍은 사진을 바로 확인할 수 있습니다. 각 이미지를 클릭하면 예쁜 테마에서 줌인 줌아웃 기능으로 더욱 자세하게 사진을 확인할 수 있습니다. 추가로 DELETE, SHARE를 클릭하면 바로 갤러리에서 이미지 삭제 또는 공유가 가능하빈다. 포켓몬스터 친구들의 사진이 담겨있는 갤러리를 자유롭게 이용해 보세요!</li>
 </ul>
 <li><h2>Pedometer</h2></li>
 <ul>
  <li>스마트폰에 있는 만보기 기능을 해당 앱에서 이용할 수 있습니다. RESET 버튼을 클릭하면 만보기 기능은 초기화되며 다시 사용할 수 있습니다. 추가로 만보기를 사용 시작한 시각과 현재까지 이용한 시간을 확인할 수 있습니다. 만보기 기능을 시작할 때 몬스터볼로 시작하게 되며 특정 걸음이 넘어가면 잉어킹 -> 갸라도스로 진화하게 됩니다. 포켓몬과 함께 진화하며 건강한 삶으로 성장하고 싶으면 해당 만보기 기능을 이용해 보세요!</li>
 </ul>
</ul>

## Authority

### - 다음의 권한들을 앱의 구동을 위하여 반드시 허용하여야 합니다.</p>

<ol>
 <li>주소록에 엑세스</li>
 <li>기기 사진,미디어, 파일 엑세스</li>
 <li>전화 걸기 및 관리</li>
 <li>사진 및 동영상 촬영</li>
 <li>신체 활동 정보 엑세스</li>
</ol>

<ul>
 <li>[주소록에 엑세스]              ->  탭1의 연락처를 구성할 때 사용합니다.</li>
 <li>[전화 걸기 및 관리]            ->  탭1에서 통화버튼을 누르면 전화로 연결됩니다.</li>
 <li>[기기 사진,미디어, 파일 엑세스] ->  탭2의 갤러리의 이미지를 구성할 때 사용합니다.</li>
 <li>[사진 및 동영상 촬영]          ->  탭2에서 이미지를 추가할 때 사용합니다.</li>
 <li>[신체 활동 정보 엑세스]        ->  Android10 이상의 기기에서 보행 탐지기 센서로 값을 읽을 때 사용합니다. </li>
</ul>

## Contact .gif
<ul>
 <li>연락처 사용 예시입니다!</li>
 <li>나만의 포켓몬과의 연락할 수 있는 유일한 방법!</li>
</ul>
<img src="https://user-images.githubusercontent.com/90249177/148053968-865b9faf-0e07-48c7-9fb2-197bf7127b37.gif" width="270px" height="480px" title="Contact" alt="Contact"></img>

## Gallery .gif
<ul>
 <li>갤러리 사용 예시입니다!</li>
 <li>사랑스러운 포켓몬을 간직할 수 있다고!</li>
</ul>
<img src="https://user-images.githubusercontent.com/90249177/148058839-3242eae9-d14c-428e-96a5-e0c29f347675.gif" width="270px" height="480px" title="Contact" alt="Contact"></img>

## Pedometer .gif
<ul>
 <li>만보기 사용 예시입니다!</li>
 <li>포켓몬과 함께 성장하고 싶다면!</li>
</ul>
<img src="https://user-images.githubusercontent.com/90249177/148058781-5c562486-0052-4b27-ac35-aae32cf7db5c.gif" width="270px" height="480px" title="Contact" alt="Contact"></img>

## Contributers

- [조원경](https://github.com/wkcho99)
- [김수민](https://github.com/SeanKim37)
