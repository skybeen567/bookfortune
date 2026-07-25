function drawBook(bookId) {
    // 모달(팝업) 창 열기
    const modal = document.getElementById('resultModal');
    const loading = document.getElementById('loading');
    const bookResult = document.getElementById('bookResult');
    
    modal.style.display = 'flex';
    loading.style.display = 'block';
    bookResult.style.display = 'none';

    // 스프링 부트 서버에 책 점괘 데이터 요청하기
    fetch(`/api/fortune?id=${bookId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('네트워크 응답에 문제가 있습니다.');
            }
            return response.json();
        })
        .then(data => {
            // 로딩 숨기고 결과 표시
            loading.style.display = 'none';
            bookResult.style.display = 'block';
            
            // 데이터 매핑
            document.getElementById('bookTitle').innerText = data.title;
            document.getElementById('bookAuthor').innerText = data.author;
            document.getElementById('fortuneText').innerText = `"${data.description}"`;
        })
        .catch(error => {
            console.error('Error:', error);
            loading.style.display = 'none';
            alert('도서 점괘를 가져오는 데 실패했습니다. 서버나 API 설정을 확인해 주세요.');
            closeModal();
        });
}

function closeModal() {
    document.getElementById('resultModal').style.display = 'none';
}

// 모달 바깥 영역 클릭 시 닫기
window.onclick = function(event) {
    const modal = document.getElementById('resultModal');
    if (event.target == modal) {
        modal.style.display = 'none';
    }
}