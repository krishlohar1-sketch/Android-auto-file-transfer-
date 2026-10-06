const $=s=>document.querySelector(s), home=$('#home'), about=$('#about'), fileInput=$('#fileInput'), receiveInput=$('#receiveInput'), status=$('#status'), list=$('#fileList'), title=$('#statusTitle');
function showFiles(files,t){status.hidden=false;title.textContent=t;list.innerHTML='';[...files].forEach(f=>{const d=document.createElement('div');d.className='file';d.innerHTML='<span>'+f.name+'</span><small>'+((f.size/1048576).toFixed(2))+' MB</small>';list.appendChild(d)})}
$('#sendBtn').onclick=()=>fileInput.click();fileInput.onchange=async()=>{if(!fileInput.files.length)return;showFiles(fileInput.files,'Selected files');if(navigator.share){try{await navigator.share({title:'Android Auto File Transfer',files:[...fileInput.files]})}catch(e){}}};
$('#receiveBtn').onclick=()=>receiveInput.click();receiveInput.onchange=()=>{if(receiveInput.files.length)showFiles(receiveInput.files,'Files selected')};
function openAbout(){home.classList.remove('active');home.setAttribute('aria-hidden','true');about.classList.add('active');about.setAttribute('aria-hidden','false');window.scrollTo(0,0)}function goHome(){about.classList.remove('active');about.setAttribute('aria-hidden','true');home.classList.add('active');home.setAttribute('aria-hidden','false');window.scrollTo(0,0)}
// Always start on Home; never restore About from a stale page state.
if(location.hash){history.replaceState(null,'',location.pathname+location.search)}goHome();
$('#aboutBtn').onclick=openAbout;$('#backBtn').onclick=goHome;
async function shareApp(){const u=location.href;if(navigator.share){try{await navigator.share({title:'Android Auto File Transfer',text:'Simple and smart file transfer app',url:u})}catch(e){}}else{try{await navigator.clipboard.writeText(u);alert('App link copied!')}catch(e){prompt('Copy app link:',u)}}}$('#shareBtn').onclick=shareApp;$('#topShare').onclick=shareApp;
let deferred=null;window.addEventListener('beforeinstallprompt',e=>{e.preventDefault();deferred=e;$('#installBtn').hidden=false});$('#installBtn').onclick=async()=>{if(deferred){await deferred.prompt();deferred=null;$('#installBtn').hidden=true}};
if('serviceWorker'in navigator)window.addEventListener('load',()=>navigator.serviceWorker.register('./sw.js?v=4').catch(()=>{}));

// Remove stale caches left by older builds after this version has loaded.
if('serviceWorker' in navigator){navigator.serviceWorker.getRegistrations().then(rs=>{rs.forEach(r=>{if(r.active && r.active.scriptURL.includes('sw.js')){} });});}
