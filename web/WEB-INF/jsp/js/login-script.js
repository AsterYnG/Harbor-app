function openPopup() {
    let popup = document.getElementById('success-popup');
    popup.style.display = 'block';
    // Установка таймера для закрытия всплывающего окна через 3 секунды (3000 миллисекунд)
    setTimeout(function() {
        popup.style.display = 'none';
    }, 3000);
}
openPopup();