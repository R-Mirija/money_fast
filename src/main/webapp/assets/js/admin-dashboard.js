function toggleAdminPopup() {
    const popup = document.getElementById('adminPopup');
    if (popup.classList.contains('show')) {
        popup.classList.remove('show');
        setTimeout(() => { popup.style.display = 'none'; }, 300);
    } else {
        popup.style.display = 'block';
        setTimeout(() => { popup.classList.add('show'); }, 10);
    }
}

function filterCountries() {
    let input = document.getElementById("countrySearch");
    let filter = input.value.toLowerCase();
    let table = document.getElementById("countryTable");
    let tr = table.getElementsByTagName("tr");

    for (let i = 0; i < tr.length; i++) {
        let td = tr[i].getElementsByTagName("td")[0];
        if (td) {
            let textValue = td.textContent || td.innerText;
            tr[i].style.display = textValue.toLowerCase().indexOf(filter) > -1 ? "" : "none";
        }
    }
}

function closeToast() {
    const toast = document.getElementById('toast-error');
    if (toast) { toast.style.opacity = '0'; setTimeout(() => { toast.style.display = 'none'; }, 500); }
}
function closeSuccessToast() {
    const toast = document.getElementById('toast-success');
    if (toast) { toast.style.opacity = '0'; setTimeout(() => { toast.style.display = 'none'; }, 500); }
}
window.addEventListener('DOMContentLoaded', () => {
    const err = document.getElementById('toast-error');
    if (err) { setTimeout(() => { closeToast(); }, 5000); }
    const succ = document.getElementById('toast-success');
    if (succ) { setTimeout(() => { closeSuccessToast(); }, 5000); }
});

// Swipe-to-delete
let startX = 0;
let currentX = 0;
let activeClientId = null;
let activeFrontElement = null;
let isDragging = false;

function startSwipe(e, clientId) {
    isDragging = true;
    activeClientId = clientId;
    activeFrontElement = document.getElementById('swipe-front-' + clientId);

    startX = e.type.startsWith('touch') ? e.touches[0].clientX : e.clientX;
    activeFrontElement.style.transition = 'none';

    document.addEventListener('mousemove', moveSwipe);
    document.addEventListener('mouseup', endSwipe);
    document.addEventListener('touchmove', moveSwipe);
    document.addEventListener('touchend', endSwipe);
}

function moveSwipe(e) {
    if (!isDragging || !activeFrontElement) return;
    currentX = e.type.startsWith('touch') ? e.touches[0].clientX : e.clientX;
    let diffX = currentX - startX;

    if (diffX < 0) diffX = 0;
    if (diffX > 80) diffX = 80;

    activeFrontElement.style.transform = "translateX(" + diffX + "px)";
}

function endSwipe() {
    if (!isDragging || !activeFrontElement) return;
    isDragging = false;

    activeFrontElement.style.transition = 'transform 0.3s cubic-bezier(0.25, 0.8, 0.25, 1)';

    let diffX = currentX - startX;
    activeFrontElement.style.transform = diffX > 40 ? 'translateX(80px)' : 'translateX(0)';

    document.removeEventListener('mousemove', moveSwipe);
    document.removeEventListener('mouseup', endSwipe);
    document.removeEventListener('touchmove', moveSwipe);
    document.removeEventListener('touchend', endSwipe);
}

function confirmDelete(event, clientId) {
    event.preventDefault();

    if (confirm("Voulez-vous supprimer définitivement ce client et son compte associé ?")) {
        event.target.submit();
    } else {
        const front = document.getElementById('swipe-front-' + clientId);
        if (front) {
            front.style.transform = 'translateX(0)';
        }
    }
}